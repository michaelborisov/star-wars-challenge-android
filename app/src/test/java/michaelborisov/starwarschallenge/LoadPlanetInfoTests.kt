package michaelborisov.starwarschallenge

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.ui.character.detail.domain.LoadPlanetInfo
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import org.junit.Before
import org.junit.Test

class LoadPlanetInfoTests {

    private lateinit var planetLoader: LoadPlanetInfo
    private lateinit var apiHelper: ApiHelper
    private lateinit var urlHelper: UrlAddressHelper

    @Before
    fun setUp() {
        apiHelper = RestStarWarsApiHelper()
        urlHelper = UrlAddressHelper()
        planetLoader = LoadPlanetInfo(
            apiHelper,
            "https://swapi.co/api/planets/",
            urlHelper
        )
    }

    @Test(timeout = 15000)
    fun smokeTest() {
        val planets = planetLoader.execute(listOf("https://swapi.co/api/planets/1/")).blockingGet()
        Assert.assertTrue(planets.isNotEmpty())
        Assert.assertTrue(planets.size == 1)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutFirstPlanet() {
        val planets = planetLoader.execute(listOf("https://swapi.co/api/planets/1/")).blockingGet()
        Assert.assertTrue(planets.isNotEmpty())
        Assert.assertTrue(planets.size == 1)
        Assert.assertEquals("Tatooine", planets[0].name)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutTwoPlanets() {
        val planets = planetLoader.execute(
            listOf(
                "https://swapi.co/api/planets/1/",
                "https://swapi.co/api/planets/2/")
        ).blockingGet()
        Assert.assertTrue(planets.isNotEmpty())
        Assert.assertTrue(planets.size == 2)
        Assert.assertEquals("Tatooine", planets[0].name)
        Assert.assertEquals("Alderaan", planets[1].name)
    }
}