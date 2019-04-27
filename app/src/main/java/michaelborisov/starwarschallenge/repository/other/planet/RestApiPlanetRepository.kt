package michaelborisov.starwarschallenge.repository.other.planet

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepositoryCommon
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

/**
 * Actual REST implementation of [PlanetRepository]
 */
class RestApiPlanetRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override var urlHelper: UrlAddressHelper
) : PlanetRepository, StarWarsUniverseRepositoryCommon() {

    override var urlCategory = "https://swapi.co/api/planets/"

    override fun getEntity(query: String): Single<Planet> {
        return restApiHelper.getPlanetInfo(getFilmIdFromUrl(query))
    }
}