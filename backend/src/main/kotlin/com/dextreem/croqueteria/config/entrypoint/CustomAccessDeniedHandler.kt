package com.dextreem.croqueteria.config.entrypoint

import com.dextreem.croqueteria.response.ApiErrorResponse.Companion.buildApiErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        val error = buildApiErrorResponse(
            status = HttpStatus.FORBIDDEN,
            message = accessDeniedException?.message,
            req = request
        )
        response!!.status = HttpStatus.FORBIDDEN.value()
        response.contentType = "application/json"
        response.setHeader("WWW-Authenticate", "")
        response.writer.write(objectMapper.writeValueAsString(error))
    }
}