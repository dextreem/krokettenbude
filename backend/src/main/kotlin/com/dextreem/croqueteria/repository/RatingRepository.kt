package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Rating
import org.springframework.data.repository.CrudRepository

interface RatingRepository : CrudRepository<Rating, Int> {
}