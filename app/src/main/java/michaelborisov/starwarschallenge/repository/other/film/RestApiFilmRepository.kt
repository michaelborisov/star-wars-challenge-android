package michaelborisov.starwarschallenge.repository.other.film

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

class RestApiFilmRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override val urlHelper: UrlAddressHelper) : FilmRepository {

    override val urlCategory = "https://swapi.co/api/films/"

    override fun getEntity(query: String): Single<Film> {
        return restApiHelper.getFilmInfo(getFilmIdFromUrl(query))
    }
}