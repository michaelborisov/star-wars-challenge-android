package michaelborisov.starwarschallenge.utils

import javax.inject.Inject

class UrlAddressHelper @Inject constructor() {

    fun getIdFromUrl(url: String, urlCategory: String): String {
        var id = url.replace(urlCategory, "")
        id = id.replace("/", "")
        return id
    }
}