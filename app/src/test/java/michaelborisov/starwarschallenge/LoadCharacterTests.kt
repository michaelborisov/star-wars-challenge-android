package michaelborisov.starwarschallenge

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.ui.character.search.domain.LoadCharacters
import org.junit.Before
import org.junit.Test

class LoadCharacterTests {

    private lateinit var characterLoader: LoadCharacters
    private lateinit var apiHelper: ApiHelper

    @Before
    fun setUp() {
        apiHelper = RestStarWarsApiHelper()
        characterLoader = LoadCharacters(apiHelper)
    }

    @Test(timeout = 15000)
    fun smokeTest() {
        val characters = characterLoader.execute("Lu").blockingGet()
        Assert.assertTrue(characters.isNotEmpty())
    }

    @Test(timeout = 15000)
    fun loadCharactersWithLuke() {
        val characters = characterLoader.execute("Luke").blockingGet()
        Assert.assertTrue(characters.isNotEmpty())
        Assert.assertTrue(characters[0].name == "Luke Skywalker")
    }

    @Test(timeout = 15000)
    fun loadCharactersWithLuke123() {
        val characters = characterLoader.execute("Luke123").blockingGet()
        Assert.assertTrue(characters.isEmpty())
    }

    @Test(timeout = 15000)
    fun loadCharactersSeveralRequests() {
        characterLoader.execute("Luke123")
        characterLoader.execute("Lu")
        val characters = characterLoader.execute("Luke").blockingGet()
        Assert.assertTrue(characters.isNotEmpty())
        Assert.assertTrue(characters[0].name == "Luke Skywalker")
    }

    @Test(timeout = 15000)
    fun loadNothingFoundSeveralRequests() {
        characterLoader.execute("Luke")
        characterLoader.execute("Lu")
        val characters = characterLoader.execute("Luke123").blockingGet()
        Assert.assertTrue(characters.isEmpty())
    }
}