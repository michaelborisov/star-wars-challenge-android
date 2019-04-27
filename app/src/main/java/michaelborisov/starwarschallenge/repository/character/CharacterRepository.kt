package michaelborisov.starwarschallenge.repository.character

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character

/**
 * Repository for loading Characters.
 */
interface CharacterRepository {

    fun getCharacterListByName(name: String): Single<List<Character>>
}