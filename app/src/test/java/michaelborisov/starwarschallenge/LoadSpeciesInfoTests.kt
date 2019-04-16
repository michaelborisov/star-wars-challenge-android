package michaelborisov.starwarschallenge

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.ui.character.detail.domain.LoadFilmInfo
import michaelborisov.starwarschallenge.ui.character.detail.domain.LoadSpeciesInfo
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import org.junit.Before
import org.junit.Test

class LoadSpeciesInfoTests {

    private lateinit var speciesLoader: LoadSpeciesInfo
    private lateinit var apiHelper: ApiHelper
    private lateinit var urlHelper: UrlAddressHelper

    @Before
    fun setUp() {
        apiHelper = RestStarWarsApiHelper()
        urlHelper = UrlAddressHelper()
        speciesLoader = LoadSpeciesInfo(
            apiHelper,
            "https://swapi.co/api/species/",
            urlHelper
        )
    }

    @Test(timeout = 15000)
    fun smokeTest() {
        val species = speciesLoader.execute(listOf("https://swapi.co/api/species/1/")).blockingGet()
        Assert.assertTrue(species.isNotEmpty())
        Assert.assertTrue(species.size == 1)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutFirstSpecies() {
        val species = speciesLoader.execute(listOf("https://swapi.co/api/species/1/")).blockingGet()
        Assert.assertTrue(species.isNotEmpty())
        Assert.assertTrue(species.size == 1)
        Assert.assertEquals("Human", species[0].name)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutTwoPlanets() {
        val species = speciesLoader.execute(
            listOf(
                "https://swapi.co/api/species/1/",
                "https://swapi.co/api/species/2/")
        ).blockingGet()
        Assert.assertTrue(species.isNotEmpty())
        Assert.assertTrue(species.size == 2)
        Assert.assertEquals("Human", species[0].name)
        Assert.assertEquals("Droid", species[1].name)
    }
}