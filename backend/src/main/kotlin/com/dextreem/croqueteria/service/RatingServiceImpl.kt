package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.request.RatingRequest
import com.dextreem.croqueteria.response.RatingResponse
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RatingServiceImpl(val ratingRepository: RatingRepository): RatingService {
    companion object : KLogging()

    @Transactional
    override fun addRating(ratingRequest: RatingRequest) : RatingResponse {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse> {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveRatingById(ratingId: Int?): RatingResponse {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun updateRating(ratingId: Int, ratingRequest: RatingRequest) {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun deleteRating(ratingId: Int) {
        throw Exception("not yet implemented")
    }
}