package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
}