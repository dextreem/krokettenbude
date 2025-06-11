package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.request.CommentRequest
import com.dextreem.croqueteria.response.CommentResponse
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(val commentRepository: CommentRepository) : CommentService {
    companion object : KLogging()

    @Transactional
    override fun addComment(commentRequest: CommentRequest): CommentResponse {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveAllComments(croquetteId: Int?): List<CommentResponse> {
        return listOf()
//        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveCommentById(commentId: Int?): CommentResponse {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun updateComment(commentId: Int, commentRequest: CommentRequest) {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun deleteComment(commentId: Int) {
        throw Exception("not yet implemented")
    }
}