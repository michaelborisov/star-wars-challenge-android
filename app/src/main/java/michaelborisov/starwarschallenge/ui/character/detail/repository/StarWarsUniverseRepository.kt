package michaelborisov.starwarschallenge.ui.character.detail.repository

import io.reactivex.Single

interface StarWarsUniverseRepository<Entity> {

    fun getEntity(query: String) : Single<Entity>
}