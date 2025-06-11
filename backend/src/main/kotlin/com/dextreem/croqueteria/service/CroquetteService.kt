package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import org.springframework.stereotype.Service

interface CroquetteService {
    fun addCroquette(croquetteRequest: CroquetteRequest): CroquetteResponse
    fun retrieveAllCroquettes(country: String?): List<CroquetteResponse>
    fun retrieveCroquetteById(croquetteId: Int?): CroquetteResponse
    fun updateCroquette(croquetteId: Int, croquetteRequest: CroquetteRequest)
    fun deleteCroquette(croquetteId: Int)
}