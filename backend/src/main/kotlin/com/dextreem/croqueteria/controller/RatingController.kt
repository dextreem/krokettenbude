package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.RatingCreateRequest
import com.dextreem.croqueteria.request.RatingUpdateRequest
import com.dextreem.croqueteria.response.RatingResponse
import com.dextreem.croqueteria.service.RatingService
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
@RequestMapping("/api/v1/ratings")
@Validated
@Tag(name = "Rating Rest API Endpoints", description = "Operations related to ratings")
class RatingController(val ratingService: RatingService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a rating",
        description = "Creates a rating for a croquette and a user."
    )
    fun addRating(@RequestBody @Valid ratingCreateRequest: RatingCreateRequest) : RatingResponse {
        return ratingService.addRating(ratingCreateRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all ratings",
        description = "Retrieves all ratings, optionally for a certain croquette."
    )
    @SecurityRequirements
    fun retrieveAllRatings(@RequestParam("croquette_id") croquetteId: Int?): List<RatingResponse> {
        return ratingService.retrieveAllRatings(croquetteId)
    }

    @GetMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single croquette rating.",
        description = "Retrieves a single rating for a user and a croquette."
    )
    @SecurityRequirements
    fun retrieveRatingForCroquetteId(@PathVariable("croquette_id") croquetteId: Int): RatingResponse {
        return ratingService.retrieveUserRatingForCroquette(croquetteId)
    }

    @PutMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a single rating",
        description = "Updates a single rating by its ID."
    )
    fun updateRating(
        @PathVariable("rating_id") ratingId: Int,
        @RequestBody ratingUpdateRequest: RatingUpdateRequest
    ) : RatingResponse {
        return ratingService.updateRating(ratingId, ratingUpdateRequest)
    }

    @DeleteMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a single rating",
        description = "Deletes a single rating by its ID."
    )
    fun deleteRating(@PathVariable("rating_id") ratingId: Int) {
        ratingService.deleteRating(ratingId)
    }

}

