package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

data class CommentResponse(
    @field:Schema(description = "Unique identifier of the comment", example = "123")
    val id: Int?,

    @field:Schema(description = "ID of the croquette this comment belongs to", example = "45")
    val croquetteId: Int?,

    @field:Schema(description = "ID of the user who made the comment", example = "10")
    val userId: Int?,

    @field:Schema(description = "Username of the commenter", example = "john_doe")
    val userName: String?,

    @field:Schema(description = "Content of the comment", example = "This croquette is amazing!")
    val comment: String,

    @field:Schema(description = "Timestamp when the comment was created", example = "2025-06-21T14:30:00Z")
    val createdAt: Date
)
