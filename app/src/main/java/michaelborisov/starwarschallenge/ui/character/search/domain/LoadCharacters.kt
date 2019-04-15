package michaelborisov.starwarschallenge.ui.character.search.domain

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.network.ApiHelper

class LoadCharacters(private val apiHelper: ApiHelper) {

    fun execute(searchQuery: String): Single<List<Character>> {
        return apiHelper.searchCharactersByName(searchQuery)
    }
}