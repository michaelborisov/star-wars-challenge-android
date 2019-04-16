package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper


/**
 * Class, providing functionality for loading information about [Planet],
 * in order not to use implementations of ApiHelper directly.
 */
class LoadPlanetInfo(
    private val apiHelper: ApiHelper,
    private val urlCategory: String,
    private val urlHelper: UrlAddressHelper
) {


    fun execute(planetUrls: List<String>): Single<List<Planet>> {
        if(planetUrls.isEmpty()){
            return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
        }
        val requests = mutableListOf<Single<Planet>>()
        for (pl in planetUrls) {
            requests.add(apiHelper.getPlanetInfo(getPlanetIdFromUrl(pl)))
        }

        return Single.zip(requests) { planetResults ->
            val result = mutableListOf<Planet>()
            for (pl in planetResults) {
                if (pl is Planet) {
                    result.add(pl)
                }
            }
            return@zip result
        }
    }

    private fun getPlanetIdFromUrl(planetUrl: String): String {
        return urlHelper.getIdFromUrl(planetUrl, urlCategory)
    }
}