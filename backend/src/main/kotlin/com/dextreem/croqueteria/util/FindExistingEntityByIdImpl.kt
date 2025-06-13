package com.dextreem.croqueteria.util

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class FindExistingEntityByIdImpl(
    val croquetteRepository: CroquetteRepository,
    val ratingRepository: RatingRepository,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository
) :
    FindExistingEntityById {
    override fun findCroquette(croquetteId: Int): Croquette {
        val croquette: Optional<Croquette> = croquetteRepository.findById(croquetteId)
        if (!croquette.isPresent) {
            throw ResourceNotFoundException("Croquette with ID $croquetteId")
        }
        return croquette.get()
    }

    override fun findRating(ratingId: Int): Rating {
        val rating: Optional<Rating> = ratingRepository.findById(ratingId)
        if (!rating.isPresent) {
            throw ResourceNotFoundException("Rating with ID $ratingId")
        }
        return rating.get()
    }

    override fun findUser(userId: Int): User {
        val user: Optional<User> = userRepository.findById(userId)
        if (!user.isPresent) {
            throw ResourceNotFoundException("No user found with ID $userId")
        }
        return user.get()
    }

    override fun findComment(commentId: Int): Comment {
        val comment: Optional<Comment> = commentRepository.findById(commentId)
        if (!comment.isPresent) {
            throw ResourceNotFoundException("No user found with ID $commentId")
        }
        return comment.get()
    }
}