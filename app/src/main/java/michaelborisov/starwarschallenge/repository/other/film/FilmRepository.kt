package michaelborisov.starwarschallenge.repository.other.film

import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.repository.other.StarWarsUniverseRepository

interface FilmRepository :
    StarWarsUniverseRepository<Film> {
}