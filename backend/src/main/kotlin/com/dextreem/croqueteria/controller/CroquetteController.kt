package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.service.CroquetteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    fun addCroquette(@RequestBody @Valid croquetteRequest: CroquetteRequest) : CroquetteResponse {
        return croquetteService.addCroquette(croquetteRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all croquettes", description = "Retrieves all croquettes with optional filters."
    )
    @SecurityRequirements
    fun retrieveAllCroquettes(@RequestParam country: String?): List<CroquetteResponse> {
        // TODO: Add other request params
        return croquetteService.retrieveAllCroquettes(country)
    }

    @GetMapping("/{croquetteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single croquette", description = "Retrieves a single croquette by its ID."
    )
    @SecurityRequirements
    fun retrieveCroquetteById(@PathVariable("croquette_id") croquetteId: Int?): CroquetteResponse {
        // TODO: Add other request params
        return croquetteService.retrieveCroquetteById(croquetteId)
    }

    @PutMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a croquette", description = "Updates a single croquette identified by its ID."
    )
    fun updateCroquette(
        @PathVariable("croquette_id") croquetteId: Int, @RequestBody croquetteRequest: CroquetteRequest
    ) {
        return croquetteService.updateCroquette(croquetteId, croquetteRequest)
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

