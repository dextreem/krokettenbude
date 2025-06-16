package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.CroquetteCreateRequest
import com.dextreem.croqueteria.request.CroquetteFilter
import com.dextreem.croqueteria.request.CroquetteUpdateRequest
import com.dextreem.croqueteria.response.CroquetteResponse

interface CroquetteService {
    fun addCroquette(croquetteCreateRequest: CroquetteCreateRequest): CroquetteResponse
    fun retrieveAllCroquettes(croquetteFilter: CroquetteFilter?): List<CroquetteResponse>
    fun retrieveCroquetteById(croquetteId: Int): CroquetteResponse
    fun updateCroquette(croquetteId: Int, croquetteUpdateRequest: CroquetteUpdateRequest) : CroquetteResponse
    fun deleteCroquette(croquetteId: Int)
}