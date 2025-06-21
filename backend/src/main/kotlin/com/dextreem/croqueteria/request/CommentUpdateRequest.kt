package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Size
import io.swagger.v3.oas.annotations.media.Schema

data class CommentUpdateRequest(
    @field:Size(min = 3, max = 10000, message = "Comment must be between 3 and 10.000 characters.")
    @field:Schema(
        description = "Updated text of the comment, optional but must be between 3 and 10,000 characters if provided.",
        example = "This is my updated comment"
    )
    val comment: String?
)
