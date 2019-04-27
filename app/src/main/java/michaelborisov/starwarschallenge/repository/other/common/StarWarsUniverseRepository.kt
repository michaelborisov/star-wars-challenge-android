package michaelborisov.starwarschallenge.repository.other.common

import io.reactivex.Single

/**
 * Interface to unify behaviour of all Repositories except CharacterRepository
 */
interface StarWarsUniverseRepository<Entity> {

    fun getEntity(query: String): Single<Entity>
}