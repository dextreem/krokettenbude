package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.RatingCreateRequest
import com.dextreem.croqueteria.request.RatingUpdateRequest
import com.dextreem.croqueteria.response.RatingResponse

interface RatingService {
    fun addRating(ratingCreateRequest: RatingCreateRequest): RatingResponse
    fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse>
    fun retrieveUserRatingForCroquette(croquetteId: Int): RatingResponse
    fun updateRating(ratingId: Int, ratingUpdateRequest: RatingUpdateRequest): RatingResponse
    fun deleteRating(ratingId: Int)
}