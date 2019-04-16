package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper

/**
 * Class, providing functionality for loading information about [Film],
 * in order not to use implementations of ApiHelper directly.
 */
class LoadFilmInfo(
    private val apiHelper: ApiHelper,
    private val urlCategory: String,
    private val urlHelper: UrlAddressHelper
) {

    fun execute(filmUrls: List<String>): Single<List<Film>> {
        if(filmUrls.isEmpty()){
            return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
        }
        val requests = mutableListOf<Single<Film>>()
        for (film in filmUrls) {
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