package michaelborisov.starwarschallenge.loader

import junit.framework.Assert
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
import michaelborisov.starwarschallenge.repository.other.film.RestApiFilmRepository
import michaelborisov.starwarschallenge.ui.character.detail.domain.LoadFilmInfo
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import org.junit.Before
import org.junit.Test

class LoadFilmInfoTests {

    private lateinit var filmLoader: LoadFilmInfo

    @Before
    fun setUp() {
        val apiHelper = RestStarWarsApiHelper()
        val urlHelper = UrlAddressHelper()
        val filmRepository = RestApiFilmRepository(apiHelper, urlHelper)
        filmLoader = LoadFilmInfo(filmRepository)
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
                "https://swapi.co/api/films/2/"
            )
        ).blockingGet()
        Assert.assertTrue(films.isNotEmpty())
        Assert.assertTrue(films.size == 2)
        Assert.assertEquals("A New Hope", films[0].title)
        Assert.assertEquals("The Empire Strikes Back", films[1].title)
    }
}