package com.dextreem.croqueteria.request.spec

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.request.CroquetteFilter
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object CroquetteSpecification {
    fun build(filter: CroquetteFilter): Specification<Croquette> {
        return Specification { root, _, cb ->

            val orPredicates = mutableListOf<Predicate>()
            val andPredicates = mutableListOf<Predicate>()

            filter.country?.let { orPredicates += cb.equal(cb.lower(root.get("country")), it.lowercase()) }
            filter.nameContains?.let { searchWord ->
                orPredicates += cb.like(cb.lower(root.get("name")), "%${searchWord.lowercase()}%")
            }
            filter.descriptionContains?.let { searchWord ->
                orPredicates += cb.like(cb.lower(root.get("description")), "%${searchWord.lowercase()}%")
            }

            filter.vegan?.let { andPredicates += cb.equal(root.get<Boolean>("vegan"), it) }
            filter.form?.let { andPredicates += cb.equal(root.get<String>("form"), it) }
            filter.crunchiness
                ?.takeIf { it.isNotEmpty() }
                ?.let { andPredicates += root.get<Int>("crunchiness").`in`(it) }
            filter.spiciness
                ?.takeIf { it.isNotEmpty() }
                ?.let { andPredicates += root.get<Int>("spiciness").`in`(it) }
            filter.minAverageRating?.let { minRating ->
                andPredicates += cb.greaterThanOrEqualTo(root.get<Double>("averageRating"), minRating)
            }

            val orPredicate = if (orPredicates.isNotEmpty()) cb.or(*orPredicates.toTypedArray()) else null

            val andPredicate = if (andPredicates.isNotEmpty()) cb.and(*andPredicates.toTypedArray()) else null

            when {
                orPredicate != null && andPredicate != null -> cb.and(andPredicate, orPredicate)
                orPredicate != null -> orPredicate
                andPredicate != null -> andPredicate
                else -> cb.conjunction()  // no filters, always true
            }
        }
    }
}
