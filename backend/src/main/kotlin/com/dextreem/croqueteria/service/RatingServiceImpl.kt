package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.exception.AccessForbiddenException
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.request.RatingCreateRequest
import com.dextreem.croqueteria.request.RatingUpdateRequest
import com.dextreem.croqueteria.response.RatingResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import com.dextreem.croqueteria.util.FindExistingEntityById
import jakarta.annotation.Resource
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class RatingServiceImpl(
    val ratingRepository: RatingRepository,
    val findAuthenticatedUser: FindAuthenticatedUser,
    val findExistingEntityById: FindExistingEntityById
) :
    RatingService {
    companion object : KLogging()

    @Transactional
    override fun addRating(ratingCreateRequest: RatingCreateRequest): RatingResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} wants to create new rating.")
        val croquette = findExistingEntityById.findCroquette(ratingCreateRequest.croquetteId)
        val rating = buildNewRating(ratingCreateRequest, actorUser, croquette)
        val existingRating = ratingRepository.findByCroquetteAndUser(croquette, actorUser)
        if(existingRating.isPresent){
            throw IllegalArgumentException("Rating of user ${actorUser.username} for croquette ${croquette.name} already exists. Please use PUT instead.")
        }
        val savedRating = ratingRepository.save(rating)
        logger.info("New rating has ID ${rating.id}")
        return buildRatingResponse(savedRating)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllRatings(croquetteId: Int?): List<RatingResponse> {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested all ratings. Croquette ID: $croquetteId")
        if (croquetteId != null) {
            val croquette = findExistingEntityById.findCroquette(croquetteId)
            return ratingRepository.findByCroquette(croquette).map { buildRatingResponse(it) }
        }
        logger.info("Retrieved all ratings. Returning.")
        return ratingRepository.findAll().map { buildRatingResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveUserRatingForCroquette(croquetteId: Int): RatingResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested rating for croquette $croquetteId")
        val croquette = findExistingEntityById.findCroquette(croquetteId)
        val rating = ratingRepository.findByCroquetteAndUser(croquette, actorUser)
        if(!rating.isPresent){
            throw ResourceNotFoundException("No rating for croquette $croquetteId. Returning blanco!")
        }
        logger.info("Retrieved rating $croquetteId. Returning")
        return buildRatingResponse(rating.get())
    }

    @Transactional
    override fun updateRating(ratingId: Int, ratingUpdateRequest: RatingUpdateRequest): RatingResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update rating with ID $ratingId")
        val rating = mergeToRatingIfExists(ratingId, ratingUpdateRequest)

        if (actorUser.id != rating.user?.id) {
            throw AccessForbiddenException("Not allowed to modify other user's ratings!")
        }

        val updatedRating = ratingRepository.save(rating)
        logger.info("Rating $ratingId succesfully updated!")
        return buildRatingResponse(updatedRating)
    }

    @Transactional
    override fun deleteRating(ratingId: Int) {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to delete rating with ID $ratingId")
        val rating = findExistingEntityById.findRating(ratingId)

        if (actorUser.id != rating.user?.id) {
            throw AccessForbiddenException("Not allowed to delete other user's ratings!")
        }

        ratingRepository.delete(rating)
        logger.info("Rating $ratingId successfully deleted.")
    }

    private fun buildNewRating(ratingCreateRequest: RatingCreateRequest, user: User, croquette: Croquette): Rating {
        return Rating(
            id = null,
            rating = ratingCreateRequest.rating,
            croquette = croquette,
            user = user,
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    private fun buildRatingResponse(rating: Rating): RatingResponse {
        return RatingResponse(
            id = rating.id,
            croquetteId = rating.croquette?.id,
            userId = rating.user?.id,
            rating = rating.rating
        )
    }

    private fun mergeToRatingIfExists(ratingId: Int, ratingUpdateRequest: RatingUpdateRequest): Rating {
        val rating = ratingRepository.findById(ratingId).orElseThrow {
            ResourceNotFoundException("User with ID $ratingId not found!")
        }

        ratingUpdateRequest.rating?.let { rating.rating = it }
        return rating

    }
}