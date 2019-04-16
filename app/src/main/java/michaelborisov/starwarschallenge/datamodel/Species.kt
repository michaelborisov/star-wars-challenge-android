package michaelborisov.starwarschallenge.datamodel

/**
 * Data class to represent [Species] entity.
 */
data class Species(
    val name: String,
    val language: String,
    val homeworld: String?
)