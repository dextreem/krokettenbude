package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CroquetteRecommendationRequest(
    @field:Min(value = 1, message = "Spiciness must be at least 1.")
    @field:Max(value = 5, message = "Spiciness cannot exceed 5.")
    @field:Schema(description = "Preferred spiciness level from 1 (mild) to 5 (very spicy)")
    val preferredSpiciness: Int,

    @field:Min(value = 1, message = "Crunchiness must be at least 1.")
    @field:Max(value = 5, message = "Crunchiness cannot exceed 5.")
    @field:Schema(description = "Preferred crunchiness level from 1 (soft) to 5 (very crunchy)")
    val preferredCrunchiness: Int,

    @field:NotNull(message = "Vegan flag is mandatory")
    @field:Schema(description = "Indicates if the croquette should be vegan")
    val vegan: Boolean,

    @field:NotNull(message = "Form is mandatory")
    @field:Schema(description = "Croquette form/type")
    val form: CroquetteForm
)
