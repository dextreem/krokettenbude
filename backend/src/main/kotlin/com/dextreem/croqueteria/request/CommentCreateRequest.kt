package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentCreateRequest(
    @Schema(description = "ID of the croquette to comment on", example = "123", required = true)
    val croquetteId: Int,

    @field:NotBlank(message = "Comment is mandatory")
    @field:Size(min = 3, max = 10000, message = "Comment must be between 3 and 10.000 characters.")
    @Schema(
        description = "Text content of the comment",
        example = "This croquette is delicious!",
        required = true,
        minLength = 3,
        maxLength = 10000
    )
    val comment: String
)
