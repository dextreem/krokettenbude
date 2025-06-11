package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.request.CommentRequest
import com.dextreem.croqueteria.response.CommentResponse
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CommentService(val commentRepository: CommentRepository) {
    companion object : KLogging()

    fun addComment(commentRequest: CommentRequest) {
        throw Exception("not yet implemented")
    }

    fun retrieveAllComments(croquetteId: Int?): List<CommentResponse> {
        throw Exception("not yet implemented")
    }

    fun retrieveCommentById(commentId: Int?): CommentResponse {
        throw Exception("not yet implemented")
    }

    fun updateComment(commentId: Int, commentRequest: CommentRequest) {
        throw Exception("not yet implemented")
    }

    fun deleteComment(commentId: Int) {
        throw Exception("not yet implemented")
    }
}