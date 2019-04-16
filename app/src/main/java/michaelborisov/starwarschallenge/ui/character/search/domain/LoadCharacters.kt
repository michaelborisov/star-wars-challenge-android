package michaelborisov.starwarschallenge.ui.character.search.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.network.ApiHelper

/**
 * Class, providing functionality for loading information about [Character],
 * in order not to use implementations of ApiHelper directly.
 */
class LoadCharacters(private val apiHelper: ApiHelper) {

    fun execute(searchQuery: String): Single<List<Character>> {
        return apiHelper.searchCharactersByName(searchQuery)
    }
}