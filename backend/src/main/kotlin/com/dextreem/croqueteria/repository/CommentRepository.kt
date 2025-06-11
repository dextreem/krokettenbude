package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Int> {
}