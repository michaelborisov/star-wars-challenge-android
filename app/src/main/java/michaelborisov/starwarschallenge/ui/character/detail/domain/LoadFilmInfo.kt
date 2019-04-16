package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper

class LoadFilmInfo(
    private val apiHelper: ApiHelper,
    private val urlCategory: String,
    private val urlHelper: UrlAddressHelper
) {

    fun execute(films: List<String>): Single<List<Film>> {
        val requests = mutableListOf<Single<Film>>()
        for (film in films) {
            requests.add(apiHelper.getFilmInfo(getFilmIdFromUrl(film)))
        }

        return Single.zip(requests) { filmResults ->
            val result = mutableListOf<Film>()
            for (film in filmResults) {
                if (film is Film) {
                    result.add(film)
                }
            }
            return@zip result
        }
    }

    private fun getFilmIdFromUrl(filmUrl: String): String {
        return urlHelper.getIdFromUrl(filmUrl, urlCategory)
    }
}