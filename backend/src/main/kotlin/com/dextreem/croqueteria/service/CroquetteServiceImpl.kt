package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteCreateRequest
import com.dextreem.croqueteria.request.CroquetteFilter
import com.dextreem.croqueteria.request.CroquetteUpdateRequest
import com.dextreem.croqueteria.request.SortDirection
import com.dextreem.croqueteria.request.spec.CroquetteSpecification
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import com.dextreem.croqueteria.util.InputSanitizer
import mu.KLogging
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date
import java.util.Optional

@Service
class CroquetteServiceImpl(
    val croquetteRepository: CroquetteRepository,
    val findAuthenticatedUser: FindAuthenticatedUser
) : CroquetteService {
    companion object : KLogging()

    @Transactional
    override fun addCroquette(croquetteCreateRequest: CroquetteCreateRequest): CroquetteResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} wants to create new croquette.")

        val croquette = buildNewCroquette(croquetteCreateRequest)
        val savedCroquette = croquetteRepository.save(croquette)
        logger.info("Croquette ${croquette.name} successfully created!")
        return buildCroquetteResponse(savedCroquette)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllCroquettes(croquetteFilter: CroquetteFilter?): List<CroquetteResponse> {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested all croquettes.")

        val filter = croquetteFilter ?: CroquetteFilter()
        val spec = CroquetteSpecification.build(filter)
        val sortBy = filter.sortBy?.fieldName ?: "createdAt"
        val direction = filter.sortDirection ?: SortDirection.DESC
        val sort = Sort.by(
            if (direction == SortDirection.ASC) Sort.Direction.ASC else Sort.Direction.DESC,
            sortBy
        )

        val croquettes = croquetteRepository.findAll(spec, sort)
        logger.info("Retrieved all delicious croquettes. Returning.")
        return croquettes.map { buildCroquetteResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveCroquetteById(croquetteId: Int): CroquetteResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested croquette with ID $croquetteId")
        val croquette = retrieveExistingCroquetteById(croquetteId)
        logger.info("Retrieved croquette ${croquette.name}. Returning.")
        return buildCroquetteResponse(croquette)
    }

    @Transactional
    override fun updateCroquette(croquetteId: Int, croquetteUpdateRequest: CroquetteUpdateRequest) : CroquetteResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update croquette with ID $croquetteId")
        val croquette = mergeToCroquetteIfExists(croquetteId, croquetteUpdateRequest)
        val updatedUser = croquetteRepository.save(croquette)
        logger.info("Croquette ${croquette.name} successfully updated.")
        return buildCroquetteResponse(updatedUser)
    }

    @Transactional
    override fun deleteCroquette(croquetteId: Int) {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} tries to delete croquette with ID $croquetteId")
        val croquette: Croquette = retrieveExistingCroquetteById(croquetteId)
        croquetteRepository.delete(croquette)
        logger.info("Croquette ${croquette.name} (ID: $croquetteId) deleted by user ${actorUser.username}")
    }

    private fun buildNewCroquette(croquetteCreateRequest: CroquetteCreateRequest): Croquette {
        return Croquette(
            id = null,
            country = InputSanitizer.sanitize(croquetteCreateRequest.country),
            name = InputSanitizer.sanitize(croquetteCreateRequest.name) ,
            description = InputSanitizer.sanitize(croquetteCreateRequest.description) ,
            spiciness = croquetteCreateRequest.spiciness,
            crunchiness = croquetteCreateRequest.crunchiness,
            vegan = croquetteCreateRequest.vegan,
            form = croquetteCreateRequest.form,
            imageUrl = InputSanitizer.sanitize(croquetteCreateRequest.imageUrl),
            createdAt = Date(),
            updatedAt = Date(),
        )
    }

    private fun mergeToCroquetteIfExists(croquetteId: Int, croquetteUpdateRequest: CroquetteUpdateRequest): Croquette {
        val croquette = croquetteRepository.findById(croquetteId).orElseThrow {
            ResourceNotFoundException("Croquette with ID $croquetteId not found!")
        }

        croquetteUpdateRequest.name?.let { croquette.name = InputSanitizer.sanitize(it) }
        croquetteUpdateRequest.country?.let { croquette.country = InputSanitizer.sanitize(it) }
        croquetteUpdateRequest.description?.let { croquette.description = InputSanitizer.sanitize(it) }
        croquetteUpdateRequest.crunchiness?.let { croquette.crunchiness = it }
        croquetteUpdateRequest.spiciness?.let { croquette.spiciness = it }
        croquetteUpdateRequest.vegan?.let { croquette.vegan = it }
        croquetteUpdateRequest.form?.let { croquette.form = it}
        croquetteUpdateRequest.imageUrl?.let { croquette.imageUrl = InputSanitizer.sanitize(it) }

        return croquette
    }

    private fun buildCroquetteResponse(croquette: Croquette): CroquetteResponse {
        return croquette.let {
            CroquetteResponse(
                id = it.id,
                country = it.country,
                name = it.name,
                description = it.description,
                crunchiness = it.crunchiness,
                spiciness = it.spiciness,
                vegan = it.vegan,
                form = it.form.form,
                imageUrl = it.imageUrl,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                averageRating = it.averageRating
            )
        }
    }

    private fun retrieveExistingCroquetteById(croquetteId: Int): Croquette {
        val user: Optional<Croquette> = croquetteRepository.findById(croquetteId)
        if (!user.isPresent) {
            throw ResourceNotFoundException("No croquette found with ID $croquetteId")
        }
        return user.get()
    }
}