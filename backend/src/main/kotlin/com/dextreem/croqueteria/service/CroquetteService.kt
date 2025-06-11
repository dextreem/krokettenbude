package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CroquetteService(val croquetteRepository: CroquetteRepository, ratingService: RatingService) {
    companion object : KLogging()

    fun addCroquette(croquetteRequest: CroquetteRequest) {
        throw Exception("Not yet implemented")
    }

    fun retrieveAllCroquettes(country: String?): List<CroquetteResponse> {
        throw Exception("Not yet implemented")
    }

    fun retrieveCroquetteById(croquetteId: Int?): CroquetteResponse {
        throw Exception("Not yet implemented")
    }

    fun updateCroquette(croquetteId: Int, croquetteRequest: CroquetteRequest) {
        throw Exception("Not yet implemented")
    }

    fun deleteCroquette(croquetteId: Int) {
        throw Exception("Not yet implemented")
    }
}