package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.request.CroquetteCreateRequest
import com.dextreem.croqueteria.request.CroquetteFilter
import com.dextreem.croqueteria.request.CroquetteSortBy
import com.dextreem.croqueteria.request.CroquetteUpdateRequest
import com.dextreem.croqueteria.request.SortDirection
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.service.CroquetteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/croquettes")
@Validated
@Tag(name = "Croquette Rest API Endpoints", description = "Operations related to croquettes.")
class CroquetteController(val croquetteService: CroquetteService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a croquette", description = "Creates a new croquette.")
    fun addCroquette(@RequestBody @Valid croquetteCreateRequest: CroquetteCreateRequest): CroquetteResponse {
        return croquetteService.addCroquette(croquetteCreateRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all croquettes",
        description = "Retrieves all croquettes with optional filters."
    )
    @SecurityRequirements
    fun retrieveAllCroquettes(
        @Parameter(description = "Filter by country")
        @RequestParam(required = false) country: String?,

        @Parameter(description = "Filter by substring in name")
        @RequestParam(required = false) nameContains: String?,

        @Parameter(description = "Filter by substring in description")
        @RequestParam(required = false) descriptionContains: String?,

        @Parameter(description = "Filter by crunchiness, multiple allowed")
        @RequestParam(required = false) crunchiness: List<Int>?,

        @Parameter(description = "Filter by spiciness, multiple allowed")
        @RequestParam(required = false) spiciness: List<Int>?,

        @Parameter(description = "Filter by minimum average rating")
        @RequestParam(required = false) minAverageRating: Double?,

        @Parameter(description = "Filter by vegan")
        @RequestParam(required = false) vegan: Boolean?,

        @Parameter(description = "Filter by form")
        @RequestParam(required = false) form: CroquetteForm?,

        @Parameter(description = "Sort by field")
        @RequestParam(required = false) sortBy: CroquetteSortBy?,

        @Parameter(description = "Sort direction: ASC or DESC")
        @RequestParam(required = false, defaultValue = "ASC") sortDirection: SortDirection = SortDirection.ASC
    ): List<CroquetteResponse> {

        val filter = CroquetteFilter(
            country = country,
            nameContains = nameContains,
            descriptionContains = descriptionContains,
            crunchiness = crunchiness,
            spiciness = spiciness,
            form = form,
            minAverageRating = minAverageRating,
            vegan = vegan,
            sortBy = sortBy,
            sortDirection = sortDirection
        )

        return croquetteService.retrieveAllCroquettes(filter)
    }

    @GetMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single croquette", description = "Retrieves a single croquette by its ID."
    )
    @SecurityRequirements
    fun retrieveCroquetteById(@PathVariable("croquette_id") croquetteId: Int): CroquetteResponse {
        return croquetteService.retrieveCroquetteById(croquetteId)
    }

    @PutMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a croquette", description = "Updates a single croquette identified by its ID."
    )
    fun updateCroquette(
        @PathVariable("croquette_id") croquetteId: Int, @RequestBody croquetteUpdateRequest: CroquetteUpdateRequest
    ): CroquetteResponse {
        return croquetteService.updateCroquette(croquetteId, croquetteUpdateRequest)
    }

    @DeleteMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a croquette", description = "Deletes a single comment identified by its ID"
    )
    fun deleteCroquette(@PathVariable("croquette_id") croquetteId: Int) {
        croquetteService.deleteCroquette(croquetteId)
    }

}

