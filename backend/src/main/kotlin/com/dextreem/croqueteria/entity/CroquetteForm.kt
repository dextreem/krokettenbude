package com.dextreem.croqueteria.entity

enum class CroquetteForm(val form: String) {
    CYLINDRIC("cylindric"),
    OVAL("oval"),
    BALL("ball"),
    DISK("disk"),
    OTHER("other");

    companion object {
        fun fromString(form: String): CroquetteForm? =
            CroquetteForm.entries.find { it.name.equals(form, ignoreCase = true) }
    }
}