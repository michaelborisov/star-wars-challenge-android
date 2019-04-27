package michaelborisov.starwarschallenge.repository.other.species

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import javax.inject.Inject

class RestApiSpeciesRepository @Inject constructor(
    private val restApiHelper: RestStarWarsApiHelper,
    override val urlHelper: UrlAddressHelper) : SpeciesRepository {

    override val urlCategory = "https://swapi.co/api/species/"

    override fun getEntity(query: String): Single<Species> {
        return restApiHelper.getSpeciesInfo(getFilmIdFromUrl(query))
    }
}