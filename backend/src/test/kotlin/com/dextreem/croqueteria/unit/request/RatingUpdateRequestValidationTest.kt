package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.request.RatingUpdateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RatingUpdateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass with valid rating`() {
        val request = RatingUpdateRequest(rating = 4)
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no violations")
    }

    @Test
    fun `should fail when rating is below minimum`() {
        val request = RatingUpdateRequest(rating = 0)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Rating must be at least 1.", violations.first().message)
    }

    @Test
    fun `should fail when rating exceeds maximum`() {
        val request = RatingUpdateRequest(rating = 6)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Rating cannot exceed 5.", violations.first().message)
    }

    @Test
    fun `should pass when rating is null`() {
        val request = RatingUpdateRequest(rating = null)
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no violations because rating is nullable")
    }
}
