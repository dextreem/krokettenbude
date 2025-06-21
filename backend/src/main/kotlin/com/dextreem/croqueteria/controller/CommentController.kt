package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.CommentCreateRequest
import com.dextreem.croqueteria.request.CommentUpdateRequest
import com.dextreem.croqueteria.response.ApiErrorResponse
import com.dextreem.croqueteria.response.CommentResponse
import com.dextreem.croqueteria.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
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
@RequestMapping("/api/v1/comments")
@Validated
@Tag(name = "Comment Rest API Endpoints", description = "Operations related to comments.")
class CommentController(val commentService: CommentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a comment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Comment created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CommentResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Croquette not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun addComment(@RequestBody @Valid commentCreateRequest: CommentCreateRequest): CommentResponse {
        return commentService.addComment(commentCreateRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all comments",
        description = "Retrieves all comments, optionally for a certain croquette."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of comments responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CommentResponse::class))
                    )
                ]
            ),
        ]
    )
    @SecurityRequirements
    fun retrieveAllComments(
        @Parameter(description = "Optional filter parameter to limit comments to a single croquette.")
        @RequestParam(value = "croquette_id")
        croquetteId: Int?
    ): List<CommentResponse> {
        return commentService.retrieveAllComments(croquetteId)
    }

    @GetMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single comment",
        description = "Retrieves a single comment by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Comment responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CommentResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Comment not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @SecurityRequirements
    fun retrieveCommentById(@PathVariable("comment_id") commentId: Int): CommentResponse {
        return commentService.retrieveCommentById(commentId)
    }

    @PutMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a comment", description = "Updates a single comment identified by its ID.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Comment updated and responded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CommentResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Comment not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Not allowed to modify another persons comment",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun updateComment(
        @PathVariable("comment_id") commentId: Int,
        @RequestBody commentUpdateRequest: CommentUpdateRequest
    ): CommentResponse {
        return commentService.updateComment(commentId, commentUpdateRequest)
    }

    @DeleteMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a comment", description = "Deletes a single comment identified by its ID.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Comment deleted",
            ),
            ApiResponse(
                responseCode = "404",
                description = "Comment not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Not allowed to modify another persons comment",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiErrorResponse::class)
                    )
                ]
            )
        ]
    )
    fun deleteComment(@PathVariable("comment_id") commentId: Int) {
        commentService.deleteComment(commentId)
    }

}

