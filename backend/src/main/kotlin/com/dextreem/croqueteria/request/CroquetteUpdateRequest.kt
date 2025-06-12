package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size


data class CroquetteUpdateRequest(
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    var name: String? = null,

    @Size(min = 3, max = 15, message = "Country must be between 3 and 15 characters.")
    var country: String? = null,

    @Size(min = 3, max = 10000, message = "Description must be between 3 and 10.000 characters.")
    var description: String? = null,

    @Min(1, message = "Crunchiness must be at least 1.")
    @Max(5, message = "Crunchiness cannot exceed 5.")
    var crunchiness: Int? = null,

    @Min(1, message = "Spiciness must be at least 1.")
    @Max(5, message = "Spiciness cannot exceed 5.")
    var spiciness: Int? = null,

    var vegan: Boolean? = null,
    var form: String? = null, // TODO: Custom form verifier
    var imageUrl: String? = null,

    )