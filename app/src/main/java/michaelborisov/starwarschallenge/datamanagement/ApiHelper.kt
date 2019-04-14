package michaelborisov.starwarschallenge.datamanagement

import io.reactivex.Single
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.Planet
import michaelborisov.starwarschallenge.Species

interface ApiHelper {

    fun searchCharactersByName(name: String): Single<List<Character>>

    fun getSpeciesInfo(species: String): Single<Species>

    fun getPlanetInfo(planet: String): Single<Planet>

    fun getFilmInfo(film: String): Single<Film>
}