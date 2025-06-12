package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date
import java.util.Optional

@Service
class CroquetteServiceImpl(
    val croquetteRepository: CroquetteRepository,
    val ratingService: RatingService,
    val findAuthenticatedUser: FindAuthenticatedUser
) : CroquetteService {
    companion object : KLogging()

    @Transactional
    override fun addCroquette(croquetteRequest: CroquetteRequest): CroquetteResponse {
        val croquette = buildNewCroquette(croquetteRequest)
        val savedCroquette = croquetteRepository.save(croquette)
        return buildCroquetteResponse(savedCroquette)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllCroquettes(country: String?): List<CroquetteResponse> {
        val actorUser : User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username}  requested all croquettes. Country: $country")
        if (country != null) {
            return croquetteRepository.findByCountry(country).map { buildCroquetteResponse(it) }
        }
        return croquetteRepository.findAll().map { buildCroquetteResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveCroquetteById(croquetteId: Int): CroquetteResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested croquette with ID $croquetteId")
        val croquette = retrieveExistingCroquetteById(croquetteId)
        return buildCroquetteResponse(croquette)
    }

    @Transactional
    override fun updateCroquette(croquetteId: Int, croquetteRequest: CroquetteRequest) : CroquetteResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update croquette with ID $croquetteId")
        val croquette = mergeToCroquetteIfExists(croquetteId, croquetteRequest)
        val updatedUser = croquetteRepository.save(croquette)
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

    private fun buildNewCroquette(croquetteRequest: CroquetteRequest): Croquette {
        return Croquette(
            id = null,
            country = croquetteRequest.country ?: "croquettistan",
            name = croquetteRequest.name ?: "Croquette Deluxe",
            description = croquetteRequest.description ?: "The worlds best croquette",
            crunchiness = croquetteRequest.crunchiness ?: 5,
            spiciness = croquetteRequest.spiciness ?: 5,
            vegan = croquetteRequest.vegan ?: false,
            form = CroquetteForm.fromString(croquetteRequest.form ?: "cylindric")
                ?: throw IllegalArgumentException("Unknown form of croquette: ${croquetteRequest.form}"),
            imageUrl = croquetteRequest.imageUrl ?: "",
            createdAt = Date(),
            updatedAt = Date(),
        )
    }

    private fun mergeToCroquetteIfExists(croquetteId: Int, croquetteRequest: CroquetteRequest): Croquette {
        val croquette = croquetteRepository.findById(croquetteId).orElseThrow {
            ResourceNotFoundException("Croquette with ID $croquetteId not found!")
        }

        croquetteRequest.name?.let { croquette.name = it }
        croquetteRequest.country?.let { croquette.country = it }
        croquetteRequest.description?.let { croquette.description = it }
        croquetteRequest.crunchiness?.let { croquette.crunchiness = it }
        croquetteRequest.spiciness?.let { croquette.spiciness = it }
        croquetteRequest.vegan?.let { croquette.vegan = it }
        croquetteRequest.form?.let {
            val formEnum = CroquetteForm.fromString(it)
                ?: throw IllegalArgumentException("Invalid croquette form: $it")
            croquette.form = formEnum
        }
        croquetteRequest.imageUrl?.let { croquette.imageUrl = it }

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
                averageRating = null
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