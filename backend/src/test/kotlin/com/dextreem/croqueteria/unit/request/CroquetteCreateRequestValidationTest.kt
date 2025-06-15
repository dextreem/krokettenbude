package com.dextreem.croqueteria.unit.request

import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.request.CroquetteCreateRequest
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CroquetteCreateRequestValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should pass validation with valid data`() {
        val request = CroquetteCreateRequest(
            name = "Croquette",
            country = "Spain",
            description = "Delicious croquette description.",
            crunchiness = 3,
            spiciness = 2,
            vegan = true,
            form = CroquetteForm.CYLINDRIC,
            imageUrl = "http://example.com/image.jpg"
        )
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Expected no validation errors")
    }

    @Test
    fun `should fail validation when name is blank`() {
        val request = validRequest().copy(name = "")
        val violations = validator.validate(request)
        assertEquals(2, violations.size)
    }

    @Test
    fun `should fail validation when country is blank`() {
        val request = validRequest().copy(country = "")
        val violations = validator.validate(request)
        assertEquals(2, violations.size)
        assertEquals("Country is mandatory", violations.first().message)
        assertEquals("country", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when description is blank`() {
        val request = validRequest().copy(description = "")
        val violations = validator.validate(request)
        assertEquals(2, violations.size)
        assertEquals("Description is mandatory", violations.first().message)
        assertEquals("description", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when crunchiness is less than 1`() {
        val request = validRequest().copy(crunchiness = 0)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Crunchiness must be at least 1.", violations.first().message)
        assertEquals("crunchiness", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when crunchiness is greater than 5`() {
        val request = validRequest().copy(crunchiness = 6)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Crunchiness cannot exceed 5.", violations.first().message)
        assertEquals("crunchiness", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when spiciness is less than 1`() {
        val request = validRequest().copy(spiciness = 0)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Spiciness must be at least 1.", violations.first().message)
        assertEquals("spiciness", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when spiciness is greater than 5`() {
        val request = validRequest().copy(spiciness = 6)
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Spiciness cannot exceed 5.", violations.first().message)
        assertEquals("spiciness", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when vegan is null`() {
        // Since vegan is Boolean (non-nullable), make it nullable to test null
        data class NullableVeganRequest(
            val name: String,
            val country: String,
            val description: String,
            val crunchiness: Int,
            val spiciness: Int,
            @field:jakarta.validation.constraints.NotNull(message = "Vegan is mandatory")
            val vegan: Boolean?,
            val form: CroquetteForm,
            val imageUrl: String
        )

        val request = NullableVeganRequest(
            name = "Croquette",
            country = "Spain",
            description = "Yummy",
            crunchiness = 3,
            spiciness = 2,
            vegan = null,
            form = CroquetteForm.BALL,
            imageUrl = "http://example.com/image.jpg"
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Vegan is mandatory", violations.first().message)
        assertEquals("vegan", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when form is null`() {
        // Similar as above, form must be nullable for test
        data class NullableFormRequest(
            val name: String,
            val country: String,
            val description: String,
            val crunchiness: Int,
            val spiciness: Int,
            val vegan: Boolean,
            @field:jakarta.validation.constraints.NotNull(message = "Form is mandatory")
            val form: CroquetteForm?,
            val imageUrl: String
        )

        val request = NullableFormRequest(
            name = "Croquette",
            country = "Spain",
            description = "Yummy",
            crunchiness = 3,
            spiciness = 2,
            vegan = true,
            form = null,
            imageUrl = "http://example.com/image.jpg"
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Form is mandatory", violations.first().message)
        assertEquals("form", violations.first().propertyPath.toString())
    }

    @Test
    fun `should fail validation when imageUrl is blank`() {
        val request = validRequest().copy(imageUrl = "")
        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("ImageUrl is mandatory", violations.first().message)
        assertEquals("imageUrl", violations.first().propertyPath.toString())
    }

    private fun validRequest() = CroquetteCreateRequest(
        name = "Croquette",
        country = "Spain",
        description = "Delicious croquette description.",
        crunchiness = 3,
        spiciness = 2,
        vegan = true,
        form = CroquetteForm.CYLINDRIC,
        imageUrl = "http://example.com/image.jpg"
    )
}
