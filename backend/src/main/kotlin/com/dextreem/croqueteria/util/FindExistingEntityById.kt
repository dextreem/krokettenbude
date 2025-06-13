package com.dextreem.croqueteria.util

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User

interface FindExistingEntityById {
    fun findCroquette(croquetteId: Int) : Croquette
    fun findRating(ratingId: Int): Rating
    fun findUser(userId: Int): User
    fun findComment(commentId: Int): Comment
}