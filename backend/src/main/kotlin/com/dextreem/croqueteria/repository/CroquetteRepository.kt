package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.request.CroquetteRequest
import com.dextreem.croqueteria.entity.Croquette
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CroquetteRepository : CrudRepository<Croquette, Int> {
    @Query("""
        SELECT 
            c.id AS id, 
            c.name AS name, 
            c.country AS country,
            c.description AS description,
            c.crunchiness AS crunchiness,
            c.spiciness AS spiciness,
            c.vegan AS vegan,
            c.form AS form,
            c.imageUrl AS imageUrl,
            c.createdAt AS createdAt,
            c.updatedAt AS updatedAt,
            AVG(r.rating) AS averageRating 
        FROM Croquette c 
        LEFT JOIN Rating r ON c.id = r.croquette.id 
        GROUP BY c.id, c.name, c.description, c.imageUrl
    """)
    fun findAllWithAverageRating(): List<CroquetteRequest>
}