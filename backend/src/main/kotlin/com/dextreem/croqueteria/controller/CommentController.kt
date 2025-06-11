package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.CommentRequest
import com.dextreem.croqueteria.response.CommentResponse
import com.dextreem.croqueteria.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/comments")
@Validated
class CommentController(val commentService: CommentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addRating(@RequestBody @Valid commentRequest: CommentRequest) {
        return commentService.addComment(commentRequest)
    }

    @GetMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllRatings(
        @PathVariable("comment_id") commentId: Int?,
        @RequestParam("croquette_id") croquetteId: Int?,
    ): List<CommentResponse> {
        return commentService.retrieveAllComments(commentId, croquetteId)
    }

    @PutMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateRating(
        @PathVariable("comment_id") commentId: Int,
        @RequestBody commentRequest: CommentRequest
    ) {
        return commentService.updateComment(commentId, commentRequest)
    }

    @DeleteMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCroquette(
        @PathVariable("comment_id") commentId: Int,
    ) {
        commentService.deleteComment(commentId)
    }

}

