package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.service.CroquetteService
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
@RequestMapping("/v1/croquettes")
@Validated
class CroquetteController(val croquetteService: CroquetteService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCroquette(@RequestBody @Valid croquetteRequest: CroquetteRequest) {
        return croquetteService.addCroquette(croquetteRequest)
    }

    @GetMapping("/{croquetteId}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCroquettes(
        @PathVariable("croquette_id") croquetteId: Int?,
        @RequestParam country: String?
    ): List<CroquetteResponse> {
        // TODO: Add other request params
        return croquetteService.retrieveAllCroquettes(croquetteId, country)
    }

    @PutMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCroquette(
        @PathVariable("croquette_id") croquetteId: Int,
        @RequestBody croquetteRequest: CroquetteRequest
    ) {
        return croquetteService.updateCroquette(croquetteId, croquetteRequest)
    }

    @DeleteMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCroquette(
        @PathVariable("croquette_id") croquetteId: Int,
    ) {
        croquetteService.deleteCroquette(croquetteId)
    }

}

