package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema

data class UserResponse(
    @field:Schema(description = "Unique identifier of the user", example = "1")
    val id: Int?,

    @field:Schema(description = "User's email address", example = "user@example.com")
    val email: String,

    @field:Schema(description = "Role assigned to the user", example = "MANAGER")
    val role: String
)
