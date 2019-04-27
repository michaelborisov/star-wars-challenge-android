package michaelborisov.starwarschallenge.repository.other.planet

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

class RestApiPlanetRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override val urlHelper: UrlAddressHelper) : PlanetRepository {

    override val urlCategory = "https://swapi.co/api/planets/"

    override fun getEntity(query: String): Single<Planet> {
        return restApiHelper.getPlanetInfo(getFilmIdFromUrl(query))
    }
}