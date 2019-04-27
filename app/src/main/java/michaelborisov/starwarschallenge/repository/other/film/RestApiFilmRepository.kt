package michaelborisov.starwarschallenge.repository.other.film

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

class RestApiFilmRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    private val urlHelper: UrlAddressHelper) : FilmRepository {

    private val urlCategory = "https://swapi.co/api/films/"

    override fun getEntity(query: String): Single<Film> {
        return restApiHelper.getFilmInfo(getFilmIdFromUrl(query))
    }

    private fun getFilmIdFromUrl(filmUrl: String): String {
        return urlHelper.getIdFromUrl(filmUrl, urlCategory)
    }
}