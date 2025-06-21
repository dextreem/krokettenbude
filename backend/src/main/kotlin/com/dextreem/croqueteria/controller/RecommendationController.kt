package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.exception.AccessForbiddenException
import com.dextreem.croqueteria.request.CroquetteLLMRecommendationRequest
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import com.dextreem.croqueteria.service.RecommendationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recommendations")
class RecommendationController(
    private val recommendationService: RecommendationService
) {

    @Value("\${spring.croqueteria.enable.llm}")
    private lateinit var enableLlm: String
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @SecurityRequirements
    @Operation(
        summary = "Calculate best fitting croquette.",
        description = "Provides a recommendation of croquettes best fitting for certain parameters."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of croquettes responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CroquetteRecommendationResponse::class))
                    )
                ]
            )
        ]
    )
    fun recommendCroquettes(
        @RequestBody request: CroquetteRecommendationRequest
    ): List<CroquetteRecommendationResponse> {
        return recommendationService.recommendCroquettes(request)
    }

    @PostMapping("/text")
    @SecurityRequirements
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Calculate best fitting croquette by user text.",
        description = "Provides a recommendation of croquettes best fitting for certain parameters."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of croquettes responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CroquetteRecommendationResponse::class))
                    )
                ]
            )
        ]
    )
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
