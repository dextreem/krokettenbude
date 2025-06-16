package com.dextreem.croqueteria.entity

enum class UserRole(val authority: String) {
    ANON("ROLE_ANON"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER");

    companion object {
        fun fromString(role: String): UserRole? = entries.find { it.name.equals(role, ignoreCase = true) }
    }
}