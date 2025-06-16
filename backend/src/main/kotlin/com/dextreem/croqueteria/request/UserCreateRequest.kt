package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank(message = "Email is mandatory")
    @field:Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters.")
    @field:Email(message = "Email format is invalid")
    val email: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    val password: String,

    val role: UserRole
)
