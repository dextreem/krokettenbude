package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class RatingUpdateRequest(
    @field:Min(1, message = "Rating must be at least 1.")
    @field:Max(5, message = "Rating cannot exceed 5.")
    @field:Schema(description = "Updated rating value between 1 and 5", example = "3", nullable = true)
    val rating: Int?
)
