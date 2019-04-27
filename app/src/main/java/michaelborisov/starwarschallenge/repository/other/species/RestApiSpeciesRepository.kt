package michaelborisov.starwarschallenge.repository.other.species

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepositoryCommon
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

/**
 * Actual REST implementation of [SpeciesRepository]
 */
class RestApiSpeciesRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override var urlHelper: UrlAddressHelper
) : SpeciesRepository, StarWarsUniverseRepositoryCommon() {

    override var urlCategory = "https://swapi.co/api/species/"

    override fun getEntity(query: String): Single<Species> {
        return restApiHelper.getSpeciesInfo(getFilmIdFromUrl(query))
    }
}