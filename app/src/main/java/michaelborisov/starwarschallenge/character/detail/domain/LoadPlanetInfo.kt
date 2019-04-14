package michaelborisov.starwarschallenge.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.Planet
import michaelborisov.starwarschallenge.Species
import michaelborisov.starwarschallenge.datamanagement.ApiHelper

class LoadPlanetInfo(private val apiHelper: ApiHelper) {

    fun execute(species: List<Species>): Single<List<Planet>> {
        val requests = mutableListOf<Single<Planet>>()
        for (sp in species) {
            if(sp.homeworld == null){
                return Single.create { emitter -> emitter.onSuccess(ArrayList<Planet>()) }
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
        var id = planetUrl.replace("https://swapi.co/api/planets/", "")
        id = id.replace("/", "")
        return id
    }
}