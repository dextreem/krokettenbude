package com.dextreem.croqueteria.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotEmpty(message = "Email is mandatory")
    @field:Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters.")
    @field:Email(message = "Email must be a valid format")
    @field:Schema(description = "User email address", example = "user@example.com")
    val email: String,

    @field:NotEmpty(message = "Password is mandatory")
    @field:Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    @field:Schema(description = "User password", example = "StrongP@ssw0rd!")
    val password: String,
)
