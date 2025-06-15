package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.request.UserCreateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserCreateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass with valid data`() {
        val request = UserCreateRequest(
            email = "user@example.com",
            password = "securePassword123",
            role = UserRole.USER
        )
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail when email is invalid`() {
        val request = UserCreateRequest(
            email = "invalid-email",
            password = "securePassword123",
            role = UserRole.USER
        )
        val violations = validator.validate(request)
        assertTrue(violations.any { it.propertyPath.toString() == "email" && it.message.contains("format") })
    }

    @Test
    fun `should fail when password is too short`() {
        val request = UserCreateRequest(
            email = "user@example.com",
            password = "short",
            role = UserRole.USER
        )
        val violations = validator.validate(request)
        assertTrue(violations.any { it.propertyPath.toString() == "password" && it.message.contains("between 8 and 1000") })
    }

    @Test
    fun `should fail when email is blank`() {
        val request = UserCreateRequest(
            email = "",
            password = "securePassword123",
            role = UserRole.USER
        )
        val violations = validator.validate(request)
        assertTrue(violations.any { it.propertyPath.toString() == "email" && it.message.contains("mandatory") })
    }
}
