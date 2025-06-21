package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CroquetteLLMRecommendationRequest(
    @field:NotBlank(message = "LLM request is mandatory")
    @field:Size(min = 3, max = 10000, message = "LLM request must be between 3 and 10.000 characters.")
    @field:Schema(
        description = "Description input for the LLM to generate croquette recommendations",
        example = "I like very hot food. I also like my croquettes soft. I'm not a vegan and I enjoy round food."
    )
    val description: String
)
