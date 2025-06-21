package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern

data class CroquetteUpdateRequest(
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    @field:Schema(description = "Name of the croquette", example = "Classic Cheese")
    var name: String? = null,

    @field:Size(min = 3, max = 15, message = "Country must be between 3 and 15 characters.")
    @field:Schema(description = "Country of origin", example = "Spain")
    var country: String? = null,

    @field:Size(min = 3, max = 10000, message = "Description must be between 3 and 10.000 characters.")
    @field:Schema(description = "Description of the croquette", example = "A delicious traditional Spanish croquette.")
    var description: String? = null,

    @field:Min(1, message = "Crunchiness must be at least 1.")
    @field:Max(5, message = "Crunchiness cannot exceed 5.")
    @field:Schema(description = "Crunchiness level from 1 (soft) to 5 (very crunchy)", example = "3")
    var crunchiness: Int? = null,

    @field:Min(1, message = "Spiciness must be at least 1.")
    @field:Max(5, message = "Spiciness cannot exceed 5.")
    @field:Schema(description = "Spiciness level from 1 (mild) to 5 (very spicy)", example = "2")
    var spiciness: Int? = null,

    @field:Schema(description = "Whether the croquette is vegan", example = "false")
    var vegan: Boolean? = null,

    @field:Schema(description = "Form/type of the croquette", example = "BALL")
    var form: CroquetteForm? = null,

    @field:Schema(description = "URL to an image of the croquette", example = "https://example.com/image.jpg")
    @field:Pattern(
        regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*\$",
        message = "Image URL must be a valid URL"
    )
    var imageUrl: String? = null,
)
