package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.exception.AccessForbiddenException
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.request.CommentCreateRequest
import com.dextreem.croqueteria.request.CommentUpdateRequest
import com.dextreem.croqueteria.response.CommentResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import com.dextreem.croqueteria.util.FindExistingEntityById
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class CommentServiceImpl(
    val commentRepository: CommentRepository,
    val findAuthenticatedUser: FindAuthenticatedUser,
    val findExistingEntityById: FindExistingEntityById
) :
    CommentService {
    companion object : KLogging()

    @Transactional
    override fun addComment(commentCreateRequest: CommentCreateRequest): CommentResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} wants to create a new comment.")
        val croquette = findExistingEntityById.findCroquette(commentCreateRequest.croquetteId)
        val comment = buildNewComment(commentCreateRequest, actorUser, croquette)
        val savedComment = commentRepository.save(comment)
        logger.info("New comment has ID ${comment.id}")
        return buildCommentResponse(savedComment)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllComments(croquetteId: Int?): List<CommentResponse> {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested all comments. Croquette ID: $croquetteId")
        if (croquetteId != null) {
            val croquette = findExistingEntityById.findCroquette(croquetteId)
            return commentRepository.findByCroquette(croquette).map { buildCommentResponse(it) }
        }
        logger.info("Retrieved all comments. Returning.")
        return commentRepository.findAll().map { buildCommentResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveCommentById(commentId: Int): CommentResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser(true)
        logger.info("User ${actorUser.username} requested comment with ID $commentId")
        val comment = findExistingEntityById.findComment(commentId)
        if (actorUser.id != comment.user?.id && actorUser.role != UserRole.MANAGER) {
            throw AccessForbiddenException("Not allowed to retrieve other user's comment as a non-manager!")
        }
        logger.info("Retrieved comment $commentId. Returning")
        return buildCommentResponse(comment)
    }

    @Transactional
    override fun updateComment(commentId: Int, commentUpdateRequest: CommentUpdateRequest): CommentResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update comment with ID $commentId")
        val comment = mergeToCommentIfExist(commentId, commentUpdateRequest)

        if (actorUser.id != comment.user?.id) {
            throw AccessForbiddenException("Not allowed to modify other user's comments!")
        }

        val updatedRating = commentRepository.save(comment)
        logger.info("Comment $commentId succesfully updated!")
        return buildCommentResponse(updatedRating)
    }

    @Transactional
    override fun deleteComment(commentId: Int) {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to delete comment with ID $commentId")
        val comment = findExistingEntityById.findComment(commentId)

        if (actorUser.id != comment.user?.id) {
            throw AccessForbiddenException("Not allowed to delete other user's comment!")
        }

        commentRepository.delete(comment)
        logger.info("Rating $commentId successfully deleted.")
    }

    private fun buildNewComment(commentCreateRequest: CommentCreateRequest, user: User, croquette: Croquette):Comment{
        return Comment(
            id = null,
            comment = commentCreateRequest.comment,
            croquette = croquette,
            user = user,
            createdAt = Date(),
            updatedAt = Date(),
        )
    }

    private fun buildCommentResponse(comment: Comment): CommentResponse{
        return CommentResponse(
            id = comment.id,
            croquetteId = comment.croquette?.id,
            userId = comment.user?.id,
            comment = comment.comment,
        )
    }

    private fun mergeToCommentIfExist(commentId: Int, commentUpdateRequest: CommentUpdateRequest): Comment{
        val comment = commentRepository.findById(commentId).orElseThrow {
            ResourceNotFoundException("Comment with ID $commentId not found!")
        }

        commentUpdateRequest.comment?.let { comment.comment = it }
        return comment
    }

}