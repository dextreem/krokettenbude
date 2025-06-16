package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.request.UserUpdateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertTrue

class UserUpdateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass with valid role and password`() {
        val request = UserUpdateRequest(password = "securePassword", role = UserRole.USER)
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should pass with null role`() {
        val request = UserUpdateRequest(password = "securePassword", role = null)
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail when password is too short`() {
        val request = UserUpdateRequest(password = "short", role = UserRole.ANON)
        val violations = validator.validate(request)
        assertTrue(violations.any { it.message.contains("Password must be between") })
    }
}
