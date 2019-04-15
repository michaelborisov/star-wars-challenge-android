package michaelborisov.starwarschallenge.datamanagement

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import michaelborisov.starwarschallenge.*
import javax.inject.Inject

class RestStarWarsApiHelper @Inject constructor() : ApiHelper {

    private var apiService: StarWarsWorldApiService =
        StarWarsWorldApiService.create()

    override fun searchCharactersByName(name: String): Single<List<Character>> {
        return apiService.searchCharacterByName(name)
            .subscribeOn(Schedulers.io())
            .flatMap {
                Single.create<List<Character>> { emitter ->
                    if (it.count == 0) {
                        emitter.onSuccess(ArrayList())
                    }
                    emitter.onSuccess(it.results)
                }
            }
    }

    override fun getSpeciesInfo(species: String): Single<Species> {
        return apiService.getSpeciesInfo(species)
            .subscribeOn(Schedulers.io())
    }

    override fun getPlanetInfo(planet: String): Single<Planet> {
        return apiService.getPlanetInfo(planet)
            .subscribeOn(Schedulers.io())
    }

    override fun getFilmInfo(film: String): Single<Film> {
        return apiService.getFilmInfo(film)
            .subscribeOn(Schedulers.io())
    }
}