package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
}