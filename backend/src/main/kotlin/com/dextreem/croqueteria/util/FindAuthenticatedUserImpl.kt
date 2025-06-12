package com.dextreem.croqueteria.util

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class FindAuthenticatedUserImpl : FindAuthenticatedUser {
    override fun getAuthenticatedUser(anonymousAllowed: Boolean): User {
        val auth = SecurityContextHolder.getContext().authentication

        if (auth == null || !auth.isAuthenticated || auth.principal == "anonymousUser" && !anonymousAllowed) {
            throw AccessDeniedException("Authentication required!")
        }

        return if (auth.principal == "anonymousUser") {
            User(id = null, email = "anon", password = "", role = UserRole.ANON)
        } else {
            auth.principal as User
        }
    }
}