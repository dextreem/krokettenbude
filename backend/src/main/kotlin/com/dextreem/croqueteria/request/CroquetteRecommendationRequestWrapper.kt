package com.dextreem.croqueteria.request

class CroquetteRecommendationRequestWrapper (
        val preferredSpiciness: Int? = null,
        val preferredCrunchiness: Int? = null,
        val vegan: Boolean? = null,
        val form: String? = null
    )