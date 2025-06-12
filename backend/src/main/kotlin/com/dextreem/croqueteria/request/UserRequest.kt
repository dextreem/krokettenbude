package com.dextreem.croqueteria.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class UserRequest(
    @NotEmpty(message = "Email is mandatory", groups = [OnCreate::class])
    @Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters.")
    val email: String? = null,
    // TODO: Custom email verifier

    @NotEmpty(message = "Password is mandatory", groups = [OnCreate::class])
    @Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    val password: String? = null,

    @NotEmpty(message = "Role is mandatory", groups = [OnCreate::class])
    @Size(min = 8, max = 1000, message = "Role must be between 8 and 1000 characters.")
    val role: String?=null
    // TODO: Custom role verifier
)