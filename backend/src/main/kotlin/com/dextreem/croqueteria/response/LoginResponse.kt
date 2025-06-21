package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @field:Schema(description = "Unique identifier of the logged-in user", example = "42")
    val id: Int?,

    @field:Schema(
        description = "JWT token issued upon successful login",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    val token: String,

    @field:Schema(description = "Email address of the user", example = "user@example.com")
    val email: String,

    @field:Schema(description = "Role assigned to the user", example = "MANAGER")
    val role: String
)
