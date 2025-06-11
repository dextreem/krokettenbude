package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.request.RatingRequest
import com.dextreem.croqueteria.response.CroquetteResponse
import com.dextreem.croqueteria.response.RatingResponse
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class RatingService(val ratingRepository: RatingRepository) {
    companion object : KLogging()

    fun addRating(ratingRequest: RatingRequest) {
        throw Exception("not yet implemented")
    }

    fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse> {
        throw Exception("not yet implemented")
    }

    fun retrieveRatingById(ratingId: Int?): RatingResponse {
        throw Exception("not yet implemented")
    }

    fun updateRating(ratingId: Int, ratingRequest: RatingRequest) {
        throw Exception("not yet implemented")
    }

    fun deleteRating(ratingId: Int) {
        throw Exception("not yet implemented")
    }
}