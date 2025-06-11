package com.dextreem.croqueteria.response


data class RatingResponse (
    val id: Int?,
    val croquetteId: Int,
    val userId: Int,
    val rating: Int
)