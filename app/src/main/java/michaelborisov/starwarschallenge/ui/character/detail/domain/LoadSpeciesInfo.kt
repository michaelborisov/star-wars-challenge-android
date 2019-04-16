package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper

class LoadSpeciesInfo(
    private val apiHelper: ApiHelper,
    private val urlCategory: String,
    private val urlHelper: UrlAddressHelper
) {

    fun execute(speciesUrls: List<String>): Single<List<Species>> {
        val requests = mutableListOf<Single<Species>>()
        for (sp in speciesUrls) {
            requests.add(apiHelper.getSpeciesInfo(getSpeciesIdFromUrl(sp)))
        }

        return Single.zip(requests) { species ->
            val result = mutableListOf<Species>()
            for (sp in species) {
                if (sp is Species) {
                    result.add(sp)
                }
            }
            return@zip result
        }
    }

    private fun getSpeciesIdFromUrl(speciesUrl: String): String {
        return urlHelper.getIdFromUrl(speciesUrl, urlCategory)
    }
}