package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty


data class RatingUpdateRequest (
    @Min(1, message = "Rating must be at least 1.")
    @Max(5, message = "Rating cannot exceed 5.")
    val rating: Int?
)