package com.dextreem.croqueteria.request

import jakarta.validation.constraints.Size

data class UserUpdateRequest(
    @Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    val password: String? = null,

    @Size(min = 8, max = 1000, message = "Role must be between 8 and 1000 characters.")
    val role: String?=null
    // TODO: Custom role verifier
)