package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RatingRepository : CrudRepository<Rating, Int> {
    fun findByCroquette(croquette: Croquette): List<Rating>
    fun findByCroquetteAndUser(croquette: Croquette, user: User): Optional<Rating>
}