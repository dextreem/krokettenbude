package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.RatingCreateRequest
import com.dextreem.croqueteria.request.RatingUpdateRequest
import com.dextreem.croqueteria.response.ApiErrorResponse
import com.dextreem.croqueteria.response.RatingResponse
import com.dextreem.croqueteria.service.RatingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/ratings")
@Validated
@Tag(name = "Rating Rest API Endpoints", description = "Operations related to ratings")
class RatingController(val ratingService: RatingService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a rating",
        description = "Creates a rating for a croquette and a user."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Rating created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RatingResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Rating already exists for this user and this croquette",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Croquette not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun addRating(@RequestBody @Valid ratingCreateRequest: RatingCreateRequest): RatingResponse {
        return ratingService.addRating(ratingCreateRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all ratings",
        description = "Retrieves all ratings, optionally for a certain croquette."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of ratings",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = RatingResponse::class))
                    )
                ]
            )
        ]
    )
    @SecurityRequirements
    fun retrieveAllRatings(
        @Parameter(description = "Optional filter parameter to limit ratings to a single croquette.")
        @RequestParam("croquette_id") croquetteId: Int?
    ): List<RatingResponse> {
        return ratingService.retrieveAllRatings(croquetteId)
    }

    @GetMapping("/{croquette_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single croquette rating",
        description = "Retrieves a single rating for a user and a croquette."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Rating responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RatingResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Rating not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun retrieveRatingForCroquetteId(@PathVariable("croquette_id") croquetteId: Int): RatingResponse {
        return ratingService.retrieveUserRatingForCroquette(croquetteId)
    }

    @PutMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a single rating",
        description = "Updates a single rating by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Rating updated",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RatingResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Not allowed to delete another persons comment",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Rating not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun updateRating(
        @PathVariable("rating_id") ratingId: Int,
        @RequestBody ratingUpdateRequest: RatingUpdateRequest
    ): RatingResponse {
        return ratingService.updateRating(ratingId, ratingUpdateRequest)
    }

    @DeleteMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a single rating",
        description = "Deletes a single rating by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Rating deleted"
            ),
            ApiResponse(
                responseCode = "403",
                description = "Not allowed to delete another persons comment",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Rating not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun deleteRating(@PathVariable("rating_id") ratingId: Int) {
        ratingService.deleteRating(ratingId)
    }
}
