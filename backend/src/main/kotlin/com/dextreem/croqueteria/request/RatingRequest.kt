package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty


data class RatingRequest (
    @NotBlank(message = "CroquetteId must not be null")
    val croquetteId: Int,

    @NotBlank(message = "CroquetteId must not be null")
    val userId: Int,

    @NotEmpty(message = "Rating is mandatory")
    @Min(1, message = "Rating must be at least 1.")
    @Max(5, message = "Rating cannot exceed 5.")
    val rating: Int
)