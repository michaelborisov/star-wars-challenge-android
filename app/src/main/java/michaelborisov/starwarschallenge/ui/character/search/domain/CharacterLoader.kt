package michaelborisov.starwarschallenge.ui.character.search.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.repository.character.CharacterRepository

/**
 * Class, providing functionality for loading information about [Character],
 * from CharacterRepository.
 */
class CharacterLoader(private val characterRepository: CharacterRepository) {

    fun execute(searchQuery: String): Single<List<Character>> {
        return characterRepository.getCharacterListByName(searchQuery)
    }
}