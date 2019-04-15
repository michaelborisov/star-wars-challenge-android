package michaelborisov.starwarschallenge.character.search.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.datamanagement.ApiHelper

class LoadCharacters(private val apiHelper: ApiHelper) {

    fun execute(searchQuery: String): Single<List<Character>> {
        return apiHelper.searchCharactersByName(searchQuery)
    }
}