package michaelborisov.starwarschallenge.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.datamanagement.ApiHelper

class LoadFilmInfo(private val apiHelper: ApiHelper) {

    fun execute(films: List<String>): Single<List<Film>> {
        val requests = mutableListOf<Single<Film>>()
        for (film in films) {
            requests.add(apiHelper.getFilmInfo(getFilmIdFromUrl(film)))
        }

        return Single.zip(requests) { filmResults ->
            val result = mutableListOf<Film>()
            for (film in filmResults) {
                if (film is Film) {
                    result.add(film)
                }
            }
            return@zip result
        }
    }

    private fun getFilmIdFromUrl(planetUrl: String): String {
        var id = planetUrl.replace("https://swapi.co/api/films/", "")
        id = id.replace("/", "")
        return id
    }
}