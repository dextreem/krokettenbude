package com.dextreem.croqueteria.response

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

data class ApiErrorResponse (
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String?
){
    companion object{
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