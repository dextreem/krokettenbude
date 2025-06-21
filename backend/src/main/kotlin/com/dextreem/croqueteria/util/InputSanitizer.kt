package com.dextreem.croqueteria.util

object InputSanitizer {

    /**
     * Sanitizes a string input by:
     * - Trimming leading/trailing whitespace
     * - Removing HTML tags (basic protection against XSS)
     * - Normalizing whitespace (e.g., multiple spaces to one)
     */
    fun sanitize(input: String?): String {
        if (input == null) return ""
        return input
            .trim()
            .replace(Regex("<.*?>"), "") // Remove HTML tags
            .replace(Regex("\\s+"), " ") // Collapse multiple spaces
    }
}
