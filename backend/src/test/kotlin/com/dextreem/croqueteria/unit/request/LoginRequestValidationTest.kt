package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.request.LoginRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation with valid email and password`() {
        val request = LoginRequest(email = "test@example.com", password = "validPassword123")
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors")
    }

    @Test
    fun `should fail when email is blank`() {
        val request = LoginRequest(email = "", password = "validPassword123")
        val violations = validator.validate(request)
        assertEquals(2, violations.size) // NotEmpty and Email
    }

    @Test
    fun `should fail when email is invalid format`() {
        val request = LoginRequest(email = "invalid-email", password = "validPassword123")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Email must be a valid format", violations.first().message)
    }

    @Test
    fun `should fail when password is too short`() {
        val request = LoginRequest(email = "test@example.com", password = "short")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Password must be between 8 and 1000 characters.", violations.first().message)
    }
}
