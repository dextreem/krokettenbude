package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.request.CroquetteUpdateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CroquetteUpdateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation with all valid fields`() {
        val request = CroquetteUpdateRequest(
            name = "Delicious Croquette",
            country = "Spain",
            description = "A tasty and crunchy croquette",
            crunchiness = 4,
            spiciness = 2,
            vegan = true,
            form = CroquetteForm.BALL,
            imageUrl = "http://image.com/croquette.jpg"
        )
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors")
    }

    @Test
    fun `should allow all fields to be null`() {
        val request = CroquetteUpdateRequest()
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors for null fields")
    }

    @Test
    fun `should fail when name is too short`() {
        val request = CroquetteUpdateRequest(name = "Hi")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Name must be between 3 and 30 characters.", violations.first().message)
    }

    @Test
    fun `should fail when country is too long`() {
        val request = CroquetteUpdateRequest(country = "ThisCountryNameIsTooLong")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Country must be between 3 and 15 characters.", violations.first().message)
    }

    @Test
    fun `should fail when description is too short`() {
        val request = CroquetteUpdateRequest(description = "Hi")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Description must be between 3 and 10.000 characters.", violations.first().message)
    }

    @Test
    fun `should fail when crunchiness is below 1`() {
        val request = CroquetteUpdateRequest(crunchiness = 0)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Crunchiness must be at least 1.", violations.first().message)
    }

    @Test
    fun `should fail when spiciness is above 5`() {
        val request = CroquetteUpdateRequest(spiciness = 6)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Spiciness cannot exceed 5.", violations.first().message)
    }
}
