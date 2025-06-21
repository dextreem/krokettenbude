package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

data class UserUpdateRequest(
    @field:Size(min = 8, max = 1000, message = "Password must be between 8 and 1000 characters.")
    @field:Schema(
        description = "New password for the user, optional",
        example = "newStrongPassword456",
        nullable = true
    )
    val password: String? = null,

    @field:Schema(
        description = "Updated user role, optional",
        example = "MANAGER",
        nullable = true
    )
    val role: UserRole? = null
)
