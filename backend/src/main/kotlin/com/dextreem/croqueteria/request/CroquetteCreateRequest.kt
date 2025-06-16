package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull

data class CroquetteCreateRequest(
    @field:NotBlank(message = "Name is mandatory")
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    var name: String,

    @field:NotBlank(message = "Country is mandatory")
    @field:Size(min = 3, max = 15, message = "Country must be between 3 and 15 characters.")
    var country: String,

    @field:NotBlank(message = "Description is mandatory")
    @field:Size(min = 3, max = 10000, message = "Description must be between 3 and 10.000 characters.")
    var description: String,

    @field:Min(value = 1, message = "Crunchiness must be at least 1.")
    @field:Max(value = 5, message = "Crunchiness cannot exceed 5.")
    var crunchiness: Int,

    @field:Min(value = 1, message = "Spiciness must be at least 1.")
    @field:Max(value = 5, message = "Spiciness cannot exceed 5.")
    var spiciness: Int,

    @field:NotNull(message = "Vegan is mandatory")
    var vegan: Boolean,

    @field:NotNull(message = "Form is mandatory")
    var form: CroquetteForm,

    @field:NotBlank(message = "ImageUrl is mandatory")
    var imageUrl: String,
)
