package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class CroquetteRecommendationRequestWrapper(
    @field:Min(1, message = "Spiciness must be at least 1.")
    @field:Max(5, message = "Spiciness cannot exceed 5.")
    @field:Schema(description = "Preferred spiciness level from 1 (mild) to 5 (very spicy)", example = "3")
    val preferredSpiciness: Int? = null,

    @field:Min(1, message = "Crunchiness must be at least 1.")
    @field:Max(5, message = "Crunchiness cannot exceed 5.")
    @field:Schema(description = "Preferred crunchiness level from 1 (soft) to 5 (very crunchy)", example = "4")
    val preferredCrunchiness: Int? = null,

    @field:Schema(description = "Indicates if the croquette should be vegan", example = "true")
    val vegan: Boolean? = null,

    @field:Schema(description = "Croquette form (e.g., 'BALL', 'STICK')", example = "BALL")
    val form: String? = null
)
