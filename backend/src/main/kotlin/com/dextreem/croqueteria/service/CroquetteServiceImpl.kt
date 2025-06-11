package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CroquetteServiceImpl(val croquetteRepository: CroquetteRepository, ratingService: RatingService) : CroquetteService {
    companion object : KLogging()

    @Transactional
    override fun addCroquette(croquetteRequest: CroquetteRequest) : CroquetteResponse {
        throw Exception("Not yet implemented")
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