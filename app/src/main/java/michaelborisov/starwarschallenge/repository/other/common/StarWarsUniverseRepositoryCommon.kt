package michaelborisov.starwarschallenge.repository.other.common

import michaelborisov.starwarschallenge.utils.UrlAddressHelper

/**
 * Helper class to remove repeating of util operation for each Repository
 */
open class StarWarsUniverseRepositoryCommon {

    protected open lateinit var urlHelper: UrlAddressHelper

    protected open lateinit var urlCategory: String

    fun getFilmIdFromUrl(filmUrl: String): String {
        return urlHelper.getIdFromUrl(filmUrl, urlCategory)
    }
}