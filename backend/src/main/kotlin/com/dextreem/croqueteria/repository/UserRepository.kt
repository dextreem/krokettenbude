package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
    fun findByRole(role:String): List<User>
}