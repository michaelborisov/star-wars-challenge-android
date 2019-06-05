package michaelborisov.starwarschallenge.loader

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.repository.character.RestApiCharacterRepository
import michaelborisov.starwarschallenge.ui.character.search.domain.LoadCharacters
import org.junit.Before
import org.junit.Test

class LoadCharacterTests {

    private lateinit var characterLoader: LoadCharacters

    @Before
    fun setUp() {
        val apiHelper = RestStarWarsApiHelper()
        val characterRepository = RestApiCharacterRepository(apiHelper)
        characterLoader = LoadCharacters(characterRepository)
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