package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CroquetteRecommendationRequest(
    @field:Min(value = 1, message = "Spiciness must be at least 1.")
    @field:Max(value = 5, message = "Spiciness cannot exceed 5.")
    val preferredSpiciness: Int,

    @field:Min(value = 1, message = "Crunchiness must be at least 1.")
    @field:Max(value = 5, message = "Crunchiness cannot exceed 5.")
    val preferredCrunchiness: Int,

    @field:NotNull(message = "Form is mandatory")
    val vegan: Boolean,

    @field:NotNull(message = "Vegan is mandatory")
    val form: CroquetteForm
)
