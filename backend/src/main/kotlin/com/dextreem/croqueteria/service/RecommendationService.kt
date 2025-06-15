package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.CroquetteLLMRecommendationRequest
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse

interface RecommendationService{
    fun recommendCroquettes(request: CroquetteRecommendationRequest): List<CroquetteRecommendationResponse>
    fun recommendCroquetteLlm(request: CroquetteLLMRecommendationRequest) : List<CroquetteRecommendationResponse>
}