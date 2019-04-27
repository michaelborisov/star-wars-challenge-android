package michaelborisov.starwarschallenge.repository.other

import io.reactivex.Single
import michaelborisov.starwarschallenge.utils.UrlAddressHelper

interface StarWarsUniverseRepository<Entity> {

    fun getEntity(query: String) : Single<Entity>

    val urlHelper: UrlAddressHelper

    val urlCategory: String

    fun getFilmIdFromUrl(filmUrl: String): String {
        return urlHelper.getIdFromUrl(filmUrl, urlCategory)
    }
}