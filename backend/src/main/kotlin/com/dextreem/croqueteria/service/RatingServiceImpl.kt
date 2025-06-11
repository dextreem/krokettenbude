package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.request.RatingRequest
import com.dextreem.croqueteria.response.RatingResponse
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RatingService(val ratingRepository: RatingRepository) {
    companion object : KLogging()

    @Transactional
    fun addRating(ratingRequest: RatingRequest) {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse> {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    fun retrieveRatingById(ratingId: Int?): RatingResponse {
        throw Exception("not yet implemented")
    }

    @Transactional
    fun updateRating(ratingId: Int, ratingRequest: RatingRequest) {
        throw Exception("not yet implemented")
    }

    @Transactional
    fun deleteRating(ratingId: Int) {
        throw Exception("not yet implemented")
    }
}