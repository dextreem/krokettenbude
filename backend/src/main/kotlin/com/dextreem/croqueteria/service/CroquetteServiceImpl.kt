package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class CroquetteServiceImpl(
    val croquetteRepository: CroquetteRepository,
    val ratingService: RatingService,
    val findAuthenticatedUser: FindAuthenticatedUser
) : CroquetteService {
    companion object : KLogging()

    @Transactional
    override fun addCroquette(croquetteRequest: CroquetteRequest): CroquetteResponse {
        val croquette = buildCroquette(croquetteRequest)
        val savedCroquette = croquetteRepository.save(croquette)
        return buildCroquetteResponse(savedCroquette)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllCroquettes(country: String?): List<CroquetteResponse> {
//        val actorUser : User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User requested all croquettes. Country: $country")
        if (country != null) {
            return croquetteRepository.findByCountry(country).map { buildCroquetteResponse(it) }
        }
        return croquetteRepository.findAll().map { buildCroquetteResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveCroquetteById(croquetteId: Int?): CroquetteResponse {
        throw Exception("Not yet implemented")
    }

    @Transactional
    override fun updateCroquette(croquetteId: Int, croquetteRequest: CroquetteRequest) {
        throw Exception("Not yet implemented")
    }

    @Transactional
    override fun deleteCroquette(croquetteId: Int) {
        throw Exception("Not yet implemented")
    }

    private fun buildCroquette(croquetteRequest: CroquetteRequest): Croquette {
        return Croquette(
            id = null,
            country = croquetteRequest.country,
            name = croquetteRequest.name,
            description = croquetteRequest.description,
            crunchiness = croquetteRequest.crunchiness,
            spiciness = croquetteRequest.spiciness,
            vegan = croquetteRequest.vegan,
            form = CroquetteForm.fromString(croquetteRequest.form)
                ?: throw IllegalArgumentException("Unknown form of croquette: ${croquetteRequest.form}"),
            imageUrl = croquetteRequest.imageUrl,
            createdAt = Date(),
            updatedAt = Date(),
        )
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
}