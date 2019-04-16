package michaelborisov.starwarschallenge

import junit.framework.Assert
import michaelborisov.starwarschallenge.utils.UrlAddressHelper
import org.junit.Before
import org.junit.Test

class UrlAddressHelperTests {

    lateinit var urlHelper: UrlAddressHelper

    @Before
    fun setUp() {
        urlHelper = UrlAddressHelper()
    }

    @Test
    fun getIdFromFilm() {
        val result = urlHelper.getIdFromUrl(
            "https://swapi.co/api/films/1/",
            "https://swapi.co/api/films/"
        ).toInt()

        Assert.assertEquals(1, result)
    }

    @Test
    fun getIdFromSpecies() {
        val result = urlHelper.getIdFromUrl(
            "https://swapi.co/api/species/2/",
            "https://swapi.co/api/species/"
        ).toInt()

        Assert.assertEquals(2, result)
    }

    @Test
    fun getIdFromPlanets() {
        val result = urlHelper.getIdFromUrl(
            "https://swapi.co/api/planets/3/",
            "https://swapi.co/api/planets/"
        ).toInt()

        Assert.assertEquals(3, result)
    }
}