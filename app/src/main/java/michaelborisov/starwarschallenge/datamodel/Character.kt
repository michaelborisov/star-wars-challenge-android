package michaelborisov.starwarschallenge.datamodel

data class Character(
    val name: String,
    val birth_year: String,
    val height: String,
    val species: List<String>,
    val films: List<String>
)

