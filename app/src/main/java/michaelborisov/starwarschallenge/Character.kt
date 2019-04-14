package michaelborisov.starwarschallenge

data class Character(
    val name: String,
    val birth_year: String,
    val height: String,
    val species: List<String>,
    val films: List<String>
)

data class CharacterResponse(val count: Int, val results: List<Character>)

data class Species(
    val name: String,
    val language: String,
    val homeworld: String?
)

data class Planet(
    val name: String,
    val population: String
)

data class Film(
    val title: String,
    val release_date: String,
    val opening_crawl: String
)
