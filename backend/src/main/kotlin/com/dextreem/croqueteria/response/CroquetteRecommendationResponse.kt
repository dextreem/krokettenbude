package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

data class CroquetteRecommendationResponse(
    @field:Schema(description = "Unique identifier of the croquette", example = "101")
    val id: Int?,

    @field:Schema(description = "Name of the croquette", example = "Classic Cheese")
    val name: String,

    @field:Schema(description = "Country of origin", example = "Spain")
    var country: String,

    @field:Schema(
        description = "Detailed description of the croquette",
        example = "A traditional Spanish croquette with creamy cheese filling."
    )
    val description: String,

    @field:Schema(description = "Crunchiness level (1 to 5)", example = "3")
    var crunchiness: Int,

    @field:Schema(description = "Spiciness level (1 to 5)", example = "2")
    var spiciness: Int,

    @field:Schema(description = "Whether the croquette is vegan", example = "false")
    var vegan: Boolean = false,

    @field:Schema(description = "Form or shape of the croquette", example = "cylindric")
    var form: String = "cylindric",

    @field:Schema(
        description = "Image URL representing the croquette",
        example = "https://example.com/images/croquette101.jpg"
    )
    var imageUrl: String = "",

    @field:Schema(description = "Creation timestamp", example = "2025-06-21T12:00:00Z", nullable = true)
    var createdAt: Date?,

    @field:Schema(description = "Last update timestamp", example = "2025-06-22T12:00:00Z", nullable = true)
    var updatedAt: Date?,

    @field:Schema(description = "Average rating given by users", example = "4.5", nullable = true)
    var averageRating: Double?,

    @field:Schema(description = "Score from recommendation engine", example = "3", nullable = true)
    var score: Int?
)
