package com.dextreem.croqueteria.response

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

data class ApiErrorResponse(
    @field:Schema(description = "Timestamp of the error response", example = "2025-06-21T15:30:00")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:Schema(description = "HTTP status code", example = "404")
    val status: Int,

    @field:Schema(description = "Error reason phrase", example = "Not Found")
    val error: String,

    @field:Schema(description = "Detailed error message", example = "User with ID 123 not found", nullable = true)
    val message: String?,

    @field:Schema(description = "Request path where the error occurred", example = "/api/v1/users/123", nullable = true)
    val path: String?
) {
    companion object {
        fun buildApiErrorResponse(
            status: HttpStatus,
            message: String?,
            req: HttpServletRequest?
        ): ResponseEntity<ApiErrorResponse> {
            val error = ApiErrorResponse(
                timestamp = LocalDateTime.now(),
                status = status.value(),
                error = status.reasonPhrase,
                message = message,
                path = req?.requestURI
            )
            return ResponseEntity.status(status).body(error)
        }
    }
}
