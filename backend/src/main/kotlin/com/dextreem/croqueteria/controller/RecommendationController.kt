package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.exception.AccessForbiddenException
import com.dextreem.croqueteria.request.CroquetteLLMRecommendationRequest
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import com.dextreem.croqueteria.service.RecommendationService
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recommendations")
class RecommendationController(
    private val recommendationService: RecommendationService
) {

    @Value("\${spring.croqueteria.enable.llm}")
    private lateinit var enableLlm: String

    @PostMapping
    @SecurityRequirements
    fun recommendCroquettes(
        @RequestBody request: CroquetteRecommendationRequest
    ): List<CroquetteRecommendationResponse> {
        return recommendationService.recommendCroquettes(request)
    }

    @PostMapping("/text")
    @SecurityRequirements
    fun recommendCroquettesByText(
        @RequestBody request: CroquetteLLMRecommendationRequest
    ): List<CroquetteRecommendationResponse> {
        if(enableLlm.lowercase() == "true"){
            return recommendationService.recommendCroquetteLlm(request)
        }else{
            throw AccessForbiddenException("LLM not enabled. Modify spring.croqueteria.enable.llm accordingly.")
        }
    }
}
