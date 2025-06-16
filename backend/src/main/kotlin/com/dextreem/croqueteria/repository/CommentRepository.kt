package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : CrudRepository<Comment, Int> {
    fun findByCroquette(croquette: Croquette): List<Comment>
}