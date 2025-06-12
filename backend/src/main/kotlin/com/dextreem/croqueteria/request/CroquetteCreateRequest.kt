package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class CroquetteCreateRequest(
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    var name: String,

    @NotEmpty(message = "Country is mandatory")
    @Size(min = 3, max = 15, message = "Country must be between 3 and 15 characters.")
    var country: String,

    @NotEmpty(message = "Description is mandatory")
    @Size(min = 3, max = 10000, message = "Description must be between 3 and 10.000 characters.")
    var description: String,

    @NotEmpty(message = "Crunchiness is mandatory")
    @Min(1, message = "Crunchiness must be at least 1.")
    @Max(5, message = "Crunchiness cannot exceed 5.")
    var crunchiness: Int,

    @NotEmpty(message = "Spiciness is mandatory")
    @Min(1, message = "Spiciness must be at least 1.")
    @Max(5, message = "Spiciness cannot exceed 5.")
    var spiciness: Int,

    @NotEmpty(message = "Vegan is mandatory")
    var vegan: Boolean,

    @NotEmpty(message = "Form is mandatory")
    var form: String, // TODO: Custom form verifier

    @NotEmpty(message = "ImgaeUrl is mandatory")
    var imageUrl: String,

    )