package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.request.CommentUpdateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals

class CommentUpdateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation when comment is null`() {
        val request = CommentUpdateRequest(comment = null)
        val violations = validator.validate(request)
        assertEquals(0, violations.size)
    }

    @Test
    fun `should fail validation when comment is too short`() {
        val request = CommentUpdateRequest(comment = "Hi")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        val violation = violations.first()
        assertEquals("Comment must be between 3 and 10.000 characters.", violation.message)
        assertEquals("comment", violation.propertyPath.toString())
    }

    @Test
    fun `should fail validation when comment is too long`() {
        val longComment = "a".repeat(10001)
        val request = CommentUpdateRequest(comment = longComment)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        val violation = violations.first()
        assertEquals("Comment must be between 3 and 10.000 characters.", violation.message)
        assertEquals("comment", violation.propertyPath.toString())
    }

    @Test
    fun `should pass validation when comment length is within bounds`() {
        val validComment = "Valid comment"
        val request = CommentUpdateRequest(comment = validComment)
        val violations = validator.validate(request)
        assertEquals(0, violations.size)
    }
}
