package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema

data class RatingResponse(
    @field:Schema(description = "Unique identifier of the rating", example = "101")
    val id: Int?,

    @field:Schema(description = "Identifier of the croquette being rated", example = "23")
    val croquetteId: Int?,

    @field:Schema(description = "Identifier of the user who submitted the rating", example = "42")
    val userId: Int?,

    @field:Schema(description = "Rating value given by the user (e.g., 1 to 5)", example = "4")
    val rating: Int
)
