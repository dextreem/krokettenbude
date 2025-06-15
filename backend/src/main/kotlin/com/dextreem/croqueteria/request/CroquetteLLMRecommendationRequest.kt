package com.dextreem.croqueteria.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CroquetteLLMRecommendationRequest(
    @field:NotBlank(message = "LLM request is mandatory")
    @field:Size(min = 3, max = 10000, message = "LLM request must be between 3 and 10.000 characters.")
    val description : String
)
