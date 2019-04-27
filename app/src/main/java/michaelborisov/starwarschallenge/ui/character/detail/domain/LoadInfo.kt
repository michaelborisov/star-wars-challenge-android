package michaelborisov.starwarschallenge.ui.character.detail.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepository

/**
 * Class for unification of behaviour of Load use-cases
 */
@Suppress("UNCHECKED_CAST")
abstract class LoadInfo<Entity>(val repository: StarWarsUniverseRepository<Entity>) {

    fun execute(urls: List<String>): Single<List<Entity>> {
        if (urls.isEmpty()) {
            return Single.create { emitter -> emitter.onSuccess(ArrayList()) }
        }
        val requests = mutableListOf<Single<Entity>>()
        for (url in urls) {
            requests.add(repository.getEntity(url))
        }

        return Single.zip(requests) { results ->
            val result = mutableListOf<Entity>()
            for (entity in results) {
                result.add(entity as Entity)
            }
            return@zip result
        }
    }

}