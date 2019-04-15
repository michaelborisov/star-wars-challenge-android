package michaelborisov.starwarschallenge.network

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species

interface ApiHelper {

    fun searchCharactersByName(name: String): Single<List<Character>>

    fun getSpeciesInfo(species: String): Single<Species>

    fun getPlanetInfo(planet: String): Single<Planet>

    fun getFilmInfo(film: String): Single<Film>
}