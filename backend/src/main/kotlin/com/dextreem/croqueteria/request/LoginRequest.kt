package com.dextreem.croqueteria.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class LoginRequest(
    @NotEmpty(message = "Email is mandatory")
    @Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters.")
    val email: String,
    // TODO: Custom email verifier

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    val password: String,

    )