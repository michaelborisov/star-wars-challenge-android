package michaelborisov.starwarschallenge.ui.character.search.repository

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character

/**
 * Repository for loading Characters.
 */
interface CharacterRepository {

    fun getCharacterListByName(name: String): Single<List<Character>>
}