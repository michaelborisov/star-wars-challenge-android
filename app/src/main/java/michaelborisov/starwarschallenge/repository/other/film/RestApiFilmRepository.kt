package michaelborisov.starwarschallenge.repository.other.film

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepositoryCommon
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

/**
 * Actual REST implementation of [FilmRepository]
 */
class RestApiFilmRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override var urlHelper: UrlAddressHelper
) : FilmRepository, StarWarsUniverseRepositoryCommon() {

    override var urlCategory = "https://swapi.co/api/films/"

    override fun getEntity(query: String): Single<Film> {
        return restApiHelper.getFilmInfo(getFilmIdFromUrl(query))
    }
}