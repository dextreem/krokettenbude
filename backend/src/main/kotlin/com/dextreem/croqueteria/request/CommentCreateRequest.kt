package com.dextreem.croqueteria.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class CommentCreateRequest(
    @NotBlank(message = "CroquetteId must not be null")
    val croquetteId: Int,

    @NotEmpty(message = "Comment is mandatory")
    @Size(min = 3, max = 10000, message = "Comment must be between 3 and 10.000 characters.")
    val comment: String
)