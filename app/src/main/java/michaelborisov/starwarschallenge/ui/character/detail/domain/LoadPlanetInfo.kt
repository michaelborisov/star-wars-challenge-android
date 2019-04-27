package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.repository.other.planet.PlanetRepository


/**
 * Class, providing functionality for loading information about [Planet].
 */
class LoadPlanetInfo(private val planetRepository: PlanetRepository) {

    fun execute(planetUrls: List<String>): Single<List<Planet>> {
        if (planetUrls.isEmpty()) {
            return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
        }
        val requests = mutableListOf<Single<Planet>>()
        for (pl in planetUrls) {
            requests.add(planetRepository.getEntity(pl))
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
}