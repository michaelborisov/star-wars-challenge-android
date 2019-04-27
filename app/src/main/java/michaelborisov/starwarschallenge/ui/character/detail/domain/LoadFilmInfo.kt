package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.ui.character.detail.repository.FilmRepository

/**
 * Class, providing functionality for loading information about [Film].
 */
class LoadFilmInfo(private val filmRepository: FilmRepository) {

    fun execute(filmUrls: List<String>): Single<List<Film>> {
        if (filmUrls.isEmpty()) {
            return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
        }
        val requests = mutableListOf<Single<Film>>()
        for (film in filmUrls) {
            requests.add(filmRepository.getEntity(film))
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
}