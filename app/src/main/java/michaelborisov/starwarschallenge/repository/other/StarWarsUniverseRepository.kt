package michaelborisov.starwarschallenge.repository.other

import io.reactivex.Single

interface StarWarsUniverseRepository<Entity> {

    fun getEntity(query: String) : Single<Entity>
}