package michaelborisov.starwarschallenge.repository.other.film

import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepository

/**
 * [FilmRepository] - should be used for implementation of all [Film] repositories
 */
interface FilmRepository :
    StarWarsUniverseRepository<Film> {
}