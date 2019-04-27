package michaelborisov.starwarschallenge.ui.character.search.repository

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import javax.inject.Inject

class RestApiCharacterRepository @Inject constructor(private val restApiHelper: RestStarWarsApiHelper) :
    CharacterRepository {

    override fun getCharacterListByName(name: String): Single<List<Character>> {
        return restApiHelper.getCharacterListByName(name)
    }
}