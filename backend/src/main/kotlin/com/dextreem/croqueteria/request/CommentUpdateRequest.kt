package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Size

data class CommentUpdateRequest(
    @field:Size(min = 3, max = 10000, message = "Comment must be between 3 and 10.000 characters.")
    val comment: String?
)
