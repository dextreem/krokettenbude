package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class CroquetteUpdateRequest(
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    var name: String? = null,

    @field:Size(min = 3, max = 15, message = "Country must be between 3 and 15 characters.")
    var country: String? = null,

    @field:Size(min = 3, max = 10000, message = "Description must be between 3 and 10.000 characters.")
    var description: String? = null,

    @field:Min(1, message = "Crunchiness must be at least 1.")
    @field:Max(5, message = "Crunchiness cannot exceed 5.")
    var crunchiness: Int? = null,

    @field:Min(1, message = "Spiciness must be at least 1.")
    @field:Max(5, message = "Spiciness cannot exceed 5.")
    var spiciness: Int? = null,

    var vegan: Boolean? = null,
    var form: CroquetteForm? = null,

    var imageUrl: String? = null,
)
