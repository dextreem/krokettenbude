package com.dextreem.croqueteria.request.spec

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.request.CroquetteFilter
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object CroquetteSpecification {
    fun build(filter: CroquetteFilter): Specification<Croquette> {
        return Specification { root, _, cb ->
            val predicates = mutableListOf<Predicate>()

            filter.country?.let { predicates += cb.equal(cb.lower(root.get("country")), it.lowercase()) }
            filter.vegan?.let { predicates += cb.equal(root.get<Boolean>("vegan"), it) }
            filter.form?.let { predicates += cb.equal(root.get<String>("form"), it) }
            filter.crunchiness
                ?.takeIf { it.isNotEmpty() }
                ?.let { predicates += root.get<Int>("crunchiness").`in`(it) }
            filter.spiciness
                ?.takeIf { it.isNotEmpty() }
                ?.let { predicates += root.get<Int>("spiciness").`in`(it) }
            filter.descriptionContains?.let { searchWord ->
                predicates += cb.like(cb.lower(root.get("description")), "%${searchWord.lowercase()}%")
            }
            filter.nameContains?.let { searchWord ->
                predicates += cb.like(cb.lower(root.get("name")), "%${searchWord.lowercase()}%")
            }
            filter.minAverageRating?.let { minRating ->
                predicates += cb.greaterThanOrEqualTo(root.get<Double>("averageRating"), minRating)
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}
