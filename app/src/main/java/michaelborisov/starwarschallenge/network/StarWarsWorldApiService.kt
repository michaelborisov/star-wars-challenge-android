package michaelborisov.starwarschallenge.network

import io.reactivex.Single
import michaelborisov.starwarschallenge.datamodel.CharacterResponse
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsWorldApiService {

    @GET("people")
    fun searchCharacterByName(@Query("search") query: String): Single<CharacterResponse>

    @GET("species/{species}/")
    fun getSpeciesInfo(@Path("species") species: String): Single<Species>

    @GET("planets/{planet}/")
    fun getPlanetInfo(@Path("planet") species: String): Single<Planet>

    @GET("films/{film}/")
    fun getFilmInfo(@Path("film") film: String): Single<Film>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): StarWarsWorldApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.co/api/")
                .build()

            return retrofit.create(StarWarsWorldApiService::class.java)
        }
    }
}