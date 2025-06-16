package com.dextreem.croqueteria.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class CommentCreateRequest(
    val croquetteId: Int,

    @field:NotBlank(message = "Comment is mandatory")
    @field:Size(min = 3, max = 10000, message = "Comment must be between 3 and 10.000 characters.")
    val comment: String
)