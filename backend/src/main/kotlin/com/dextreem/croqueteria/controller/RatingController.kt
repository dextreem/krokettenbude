package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.RatingRequest
import com.dextreem.croqueteria.response.RatingResponse
import com.dextreem.croqueteria.service.RatingService
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
@RequestMapping("/v1/ratings")
@Validated
class RatingController(val ratingService: RatingService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addRating(@RequestBody @Valid ratingRequest: RatingRequest) {
        return ratingService.addRating(ratingRequest)
    }

    @GetMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllRatings(
        @PathVariable("rating_id") ratingId: Int?,
        @RequestParam("croquette_id") croquetteId: Int?,
    ): List<RatingResponse> {
        return ratingService.retrieveAllRatings(ratingId, croquetteId)
    }

    @PutMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateRating(
        @PathVariable("rating_id") ratingId: Int,
        @RequestBody ratingRequest: RatingRequest
    ) {
        return ratingService.updateRating(ratingId, ratingRequest)
    }

    @DeleteMapping("/{rating_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCroquette(
        @PathVariable("rating_id") ratingId: Int,
    ) {
        ratingService.deleteCroquette(ratingId)
    }

}

