package michaelborisov.starwarschallenge.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlAddressHelper @Inject constructor() {

    fun getIdFromUrl(url: String, urlCategory: String): String {
        var id = url.replace(urlCategory, "")
        id = id.replace(slash, "")
        return id
    }
}