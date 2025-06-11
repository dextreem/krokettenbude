package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Comment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : CrudRepository<Comment, Int> {
}