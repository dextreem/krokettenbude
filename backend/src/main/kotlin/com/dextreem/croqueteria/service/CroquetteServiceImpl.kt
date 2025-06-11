package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Croquette
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
        val croquette = Croquette(
            id = null,
            country = croquetteRequest.country,
            name = croquetteRequest.name,
            description = croquetteRequest.description,
            crunchiness = croquetteRequest.crunchiness,
            spiciness = croquetteRequest.spiciness,
            vegan = croquetteRequest.vegan,
            form = croquetteRequest.form,
            imageUrl = croquetteRequest.imageUrl,
            createdAt = Date(),
            updatedAt = Date(),
        )

        val savedCroquette = croquetteRepository.save(croquette)

        return savedCroquette.let{ CroquetteResponse(
            id = null,
            country = it.country,
            name = it.name,
            description = it.description,
            crunchiness = it.crunchiness,
            spiciness = it.spiciness,
            vegan = it.vegan,
            form = it.form,
            imageUrl = it.imageUrl,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            averageRating = null
        ) }
    }

    @Transactional(readOnly = true)
    override fun retrieveAllCroquettes(country: String?): List<CroquetteResponse> {
        throw Exception("Not yet implemented")
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
}