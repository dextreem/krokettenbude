package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.request.CommentCreateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommentCreateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should fail validation when comment is empty`() {
        val request = CommentCreateRequest(croquetteId = 1, comment = "")
        val violations = validator.validate(request)

        assertEquals(2, violations.size)
    }

    @Test
    fun `should fail validation when comment is too short`() {
        val request = CommentCreateRequest(croquetteId = 1, comment = "Hi")
        val violations = validator.validate(request)

        assertEquals(1, violations.size)
        val violation = violations.first()
        assertEquals("Comment must be between 3 and 10.000 characters.", violation.message)
        assertEquals("comment", violation.propertyPath.toString())
    }

    @Test
    fun `should pass validation when comment is valid`() {
        val request = CommentCreateRequest(croquetteId = 1, comment = "This is a valid comment.")
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty())
    }
}
