package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import com.dextreem.croqueteria.service.RecommendationService
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recommendations")
class RecommendationController(
    private val recommendationService: RecommendationService
) {

    @PostMapping
    @SecurityRequirements
    fun recommendCroquettes(
        @RequestBody request: CroquetteRecommendationRequest
    ): List<CroquetteRecommendationResponse> {
        return recommendationService.recommendCroquettes(request)
    }
}
