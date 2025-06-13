package com.dextreem.croqueteria.request

enum class CroquetteSortBy(val fieldName: String){
    NAME("name"),
    COUNTRY("country"),
    CRUNCHINESS("crunchiness"),
    SPICINESS("spiciness"),
    AVERAGE_RATING("averageRating"),
    CREATED_AT("createdAt")
}

enum class SortDirection{
    ASC, DESC
}

data class CroquetteFilter (
    val nameContains: String? = null,
    val country: String? = null,
    val descriptionContains: String? = null,
    val crunchiness: List<Int>? = null,
    val minAverageRating: Double? = null,
    val vegan: Boolean? = null,
    val sortBy: CroquetteSortBy? = null,
    val sortDirection: SortDirection? = SortDirection.ASC
)