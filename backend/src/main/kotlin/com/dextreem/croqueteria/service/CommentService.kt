package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.CommentCreateRequest
import com.dextreem.croqueteria.request.CommentUpdateRequest
import com.dextreem.croqueteria.response.CommentResponse

interface CommentService {

    fun addComment(commentCreateRequest: CommentCreateRequest): CommentResponse
    fun retrieveAllComments(croquetteId: Int?): List<CommentResponse>
    fun retrieveCommentById(commentId: Int): CommentResponse
    fun updateComment(commentId: Int, commentUpdateRequest: CommentUpdateRequest): CommentResponse
    fun deleteComment(commentId: Int)
}