package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class RatingCreateRequest(
    @field:Schema(description = "ID of the croquette to rate", example = "123")
    val croquetteId: Int,

    @field:NotNull(message = "Rating is mandatory")
    @field:Min(1, message = "Rating must be at least 1.")
    @field:Max(5, message = "Rating cannot exceed 5.")
    @field:Schema(description = "Rating value between 1 and 5", example = "4")
    val rating: Int
)