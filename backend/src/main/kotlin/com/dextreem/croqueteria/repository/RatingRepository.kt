package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Rating
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository : CrudRepository<Rating, Int> {
}