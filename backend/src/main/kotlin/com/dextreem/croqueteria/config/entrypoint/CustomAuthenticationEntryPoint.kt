package com.dextreem.croqueteria.config.entrypoint

import com.dextreem.croqueteria.response.ApiErrorResponse.Companion.buildApiErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val error = buildApiErrorResponse(
            status = HttpStatus.UNAUTHORIZED,
            message = authException?.message,
            req = request
        )
        response!!.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"
        response.setHeader("WWW-Authenticate", "")
        response.writer.write(objectMapper.writeValueAsString(error))
    }
}