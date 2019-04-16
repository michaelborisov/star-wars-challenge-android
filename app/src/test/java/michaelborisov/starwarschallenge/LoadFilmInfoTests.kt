package michaelborisov.starwarschallenge

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.ui.character.detail.domain.LoadFilmInfo
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import org.junit.Before
import org.junit.Test

class LoadFilmInfoTests {

    private lateinit var filmLoader: LoadFilmInfo
    private lateinit var apiHelper: ApiHelper
    private lateinit var urlHelper: UrlAddressHelper

    @Before
    fun setUp() {
        apiHelper = RestStarWarsApiHelper()
        urlHelper = UrlAddressHelper()
        filmLoader = LoadFilmInfo(
            apiHelper,
            "https://swapi.co/api/films/",
            urlHelper
        )
    }

    @Test(timeout = 15000)
    fun smokeTest() {
        val films = filmLoader.execute(listOf("https://swapi.co/api/films/1/")).blockingGet()
        Assert.assertTrue(films.isNotEmpty())
        Assert.assertTrue(films.size == 1)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutFirstFilm() {
        val films = filmLoader.execute(listOf("https://swapi.co/api/films/1/")).blockingGet()
        Assert.assertTrue(films.isNotEmpty())
        Assert.assertTrue(films.size == 1)
        Assert.assertEquals("A New Hope", films[0].title)
    }

    @Test(timeout = 15000)
    fun loadInfoAboutTwoFilms() {
        val films = filmLoader.execute(
            listOf(
                "https://swapi.co/api/films/1/",
                "https://swapi.co/api/films/2/")
        ).blockingGet()
        Assert.assertTrue(films.isNotEmpty())
        Assert.assertTrue(films.size == 2)
        Assert.assertEquals("A New Hope", films[0].title)
        Assert.assertEquals("The Empire Strikes Back", films[1].title)
    }
}