package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

data class CroquetteResponse(
    @field:Schema(description = "Unique identifier of the croquette", example = "123")
    val id: Int?,

    @field:Schema(description = "Name of the croquette", example = "Classic Cheese")
    val name: String,

    @field:Schema(description = "Country of origin", example = "Spain")
    var country: String,

    @field:Schema(
        description = "Detailed description of the croquette",
        example = "A delicious croquette filled with creamy cheese."
    )
    val description: String,

    @field:Schema(description = "Crunchiness level (1 to 5)", example = "4")
    var crunchiness: Int,

    @field:Schema(description = "Spiciness level (1 to 5)", example = "2")
    var spiciness: Int,

    @field:Schema(description = "Indicates if the croquette is vegan", example = "false")
    var vegan: Boolean = false,

    @field:Schema(description = "Shape or form of the croquette", example = "cylindric")
    var form: String = "cylindric",

    @field:Schema(
        description = "URL to an image of the croquette",
        example = "https://example.com/images/croquette123.jpg"
    )
    var imageUrl: String = "",

    @field:Schema(
        description = "Date when the croquette was created",
        example = "2025-06-21T12:00:00Z",
        nullable = true
    )
    var createdAt: Date?,

    @field:Schema(
        description = "Date when the croquette was last updated",
        example = "2025-06-22T12:00:00Z",
        nullable = true
    )
    var updatedAt: Date?,

    @field:Schema(description = "Average rating from users", example = "4.3", nullable = true)
    var averageRating: Double?
)
