package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.RatingRequest
import com.dextreem.croqueteria.response.RatingResponse

interface RatingServiceImpl {
    fun addRating(ratingRequest: RatingRequest): RatingResponse
    fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse>
    fun retrieveRatingById(ratingId: Int?): RatingResponse
    fun updateRating(ratingId: Int, ratingRequest: RatingRequest)
    fun deleteRating(ratingId: Int)
}