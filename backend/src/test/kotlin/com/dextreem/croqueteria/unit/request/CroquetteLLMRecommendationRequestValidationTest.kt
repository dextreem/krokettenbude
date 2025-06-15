package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.request.CroquetteLLMRecommendationRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CroquetteLLMRecommendationRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation with valid description`() {
        val request = CroquetteLLMRecommendationRequest(description = "This is a valid LLM prompt.")
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors")
    }

    @Test
    fun `should fail validation when description is blank`() {
        val request = CroquetteLLMRecommendationRequest(description = "")
        val violations = validator.validate(request)

        assertEquals(2, violations.size, "Expected two validation violations")

        val messages = violations.map { it.message }.toSet()
        val paths = violations.map { it.propertyPath.toString() }.toSet()

        assertTrue("LLM request is mandatory" in messages)
        assertTrue("LLM request must be between 3 and 10.000 characters." in messages)
        assertEquals(setOf("description"), paths)
    }

    @Test
    fun `should fail validation when description is too short`() {
        val request = CroquetteLLMRecommendationRequest(description = "Hi")
        val violations = validator.validate(request)

        assertEquals(1, violations.size)
        val violation = violations.first()

        assertEquals("LLM request must be between 3 and 10.000 characters.", violation.message)
        assertEquals("description", violation.propertyPath.toString())
    }
}
