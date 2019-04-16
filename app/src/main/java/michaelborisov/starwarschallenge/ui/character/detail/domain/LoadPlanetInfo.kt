package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper

class LoadPlanetInfo(
    private val apiHelper: ApiHelper,
    private val urlCategory: String,
    private val urlHelper: UrlAddressHelper
) {

    fun execute(species: List<Species>): Single<List<Planet>> {
        val requests = mutableListOf<Single<Planet>>()
        for (sp in species) {
            if (sp.homeworld == null) {
                return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
            }
            requests.add(apiHelper.getPlanetInfo(getPlanetIdFromUrl(sp.homeworld)))
        }

        return Single.zip(requests) { planets ->
            val result = mutableListOf<Planet>()
            for (pl in planets) {
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