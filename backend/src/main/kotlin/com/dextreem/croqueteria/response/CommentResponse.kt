package com.dextreem.croqueteria.response


data class CommentResponse(
    val id: Int?,
    val croquetteId: Int,
    val userId: Int,
    val comment: String
)