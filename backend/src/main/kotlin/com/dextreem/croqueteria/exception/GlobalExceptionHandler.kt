package com.dextreem.croqueteria.exception

import com.dextreem.croqueteria.response.ApiErrorResponse
import com.dextreem.croqueteria.response.ApiErrorResponse.Companion.buildApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    companion object : KLogging()

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> =
        buildApiErrorResponse(HttpStatus.BAD_REQUEST, ex.message, req)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> =
        buildApiErrorResponse(HttpStatus.BAD_REQUEST, ex.message, req)

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> =
        buildApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.message, req)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse>{
        val message = ex.bindingResult.fieldErrors.joinToString("; ") { "${it.field}: ${it.defaultMessage}"  }
        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, message, req)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> {
        logger.error(ex.message, ex)
        return buildApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occured", req)
    }

    @ExceptionHandler(AccessForbiddenException::class)
    fun handleNotFound(ex: AccessForbiddenException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> =
        buildApiErrorResponse(HttpStatus.FORBIDDEN, ex.message, req)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException, req: HttpServletRequest): ResponseEntity<ApiErrorResponse> =
        buildApiErrorResponse(HttpStatus.NOT_FOUND, ex.message, req)
}
