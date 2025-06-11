package com.dextreem.croqueteria.util

import com.dextreem.croqueteria.entity.User
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class FindAuthenticatedUserImpl : FindAuthenticatedUser {
    override fun getAuthenticatedUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        if(authentication == null || !authentication.isAuthenticated || authentication.principal.equals("anonymousUser")){
            throw AccessDeniedException("authentication required!")
        }

        return authentication.principal as User
    }
}