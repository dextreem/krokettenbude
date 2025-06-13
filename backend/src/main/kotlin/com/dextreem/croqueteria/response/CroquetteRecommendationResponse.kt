package com.dextreem.croqueteria.response

import java.util.Date

data class CroquetteRecommendationResponse (
    val id: Int?,
    val name: String,
    var country: String,
    val description: String,
    var crunchiness: Int,
    var spiciness: Int,
    var vegan: Boolean = false,
    var form: String = "cylindric",
    var imageUrl: String = "",
    var createdAt: Date,
    var updatedAt: Date,
    var averageRating: Double?,
    var score: Int?
)