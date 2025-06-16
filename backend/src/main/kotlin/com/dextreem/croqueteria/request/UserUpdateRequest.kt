package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.UserRole
import jakarta.validation.constraints.Size

data class UserUpdateRequest(
    @field:Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    val password: String? = null,

    val role: UserRole? = null
)
