package com.dextreem.croqueteria.response

import java.util.Date


data class CommentResponse(
    val id: Int?,
    val croquetteId: Int?,
    val userId: Int?,
    val userName: String?,
    val comment: String,
    val createdAt: Date
)