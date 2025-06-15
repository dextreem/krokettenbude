package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CroquetteRecommendationRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation with valid values`() {
        val request = CroquetteRecommendationRequest(
            preferredSpiciness = 3,
            preferredCrunchiness = 4,
            vegan = true,
            form = CroquetteForm.BALL
        )
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors")
    }

    @Test
    fun `should fail when spiciness is below min`() {
        val request = CroquetteRecommendationRequest(
            preferredSpiciness = 0,
            preferredCrunchiness = 3,
            vegan = false,
            form = CroquetteForm.CYLINDRIC
        )
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Spiciness must be at least 1.", violations.first().message)
    }

    @Test
    fun `should fail when crunchiness is above max`() {
        val request = CroquetteRecommendationRequest(
            preferredSpiciness = 2,
            preferredCrunchiness = 6,
            vegan = false,
            form = CroquetteForm.BALL
        )
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Crunchiness cannot exceed 5.", violations.first().message)
    }

}
