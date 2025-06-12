package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.CommentRequest
import com.dextreem.croqueteria.response.CommentResponse

interface CommentService {

    fun addComment(commentRequest: CommentRequest) : CommentResponse
    fun retrieveAllComments(croquetteId: Int?): List<CommentResponse>
    fun retrieveCommentById(commentId: Int?): CommentResponse
    fun updateComment(commentId: Int, commentRequest: CommentRequest)
    fun deleteComment(commentId: Int)
}