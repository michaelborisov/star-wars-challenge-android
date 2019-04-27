package michaelborisov.starwarschallenge.network

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species

/**
 * Interface, which describes methods, available in API.
 */
interface ApiHelper {

    /**
     * Performs search of [Character] by name, provided as a [String].
     */
    fun getCharacterListByName(name: String): Single<List<Character>>

    /**
     * Performs search of [Species] by id, provided as a [String]
     */
    fun getSpeciesInfo(species: String): Single<Species>

    /**
     * Performs search of [Planet] by id, provided as a [String]
     */
    fun getPlanetInfo(planet: String): Single<Planet>

    /**
     * Performs search of [Film] by id, provided as a [String]
     */
    fun getFilmInfo(film: String): Single<Film>
}