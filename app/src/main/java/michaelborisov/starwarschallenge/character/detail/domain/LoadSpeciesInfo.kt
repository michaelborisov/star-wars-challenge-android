package michaelborisov.starwarschallenge.character.detail.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import michaelborisov.starwarschallenge.Species
import michaelborisov.starwarschallenge.datamanagement.ApiHelper

class LoadSpeciesInfo(private val apiHelper: ApiHelper) {

    fun execute(species: List<String>): Single<List<Species>> {
        val requests = mutableListOf<Single<Species>>()
        for (sp in species) {
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
        var id = speciesUrl.replace("https://swapi.co/api/species/", "")
        id = id.replace("/", "")
        return id
    }
}