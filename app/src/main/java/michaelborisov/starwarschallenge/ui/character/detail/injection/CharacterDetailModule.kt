package michaelborisov.starwarschallenge.ui.character.detail.injection

import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import michaelborisov.starwarschallenge.repository.other.film.RestApiFilmRepository
import michaelborisov.starwarschallenge.repository.other.planet.RestApiPlanetRepository
import michaelborisov.starwarschallenge.repository.other.species.RestApiSpeciesRepository
import michaelborisov.starwarschallenge.ui.character.detail.domain.FilmInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.domain.PlanetInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.domain.SpeciesInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.presenter.CharacterDetailPresenter

@Module
class CharacterDetailModule {

    @Provides
    fun providePresenter(injector: MembersInjector<CharacterDetailPresenter>): CharacterDetailPresenter {
        val presenter = CharacterDetailPresenter()
        injector.injectMembers(presenter)
        return presenter
    }

    @Provides
    fun providePlanetInfoLoader(repository: RestApiPlanetRepository): PlanetInfoLoader {
        return PlanetInfoLoader(repository)
    }

    @Provides
    fun provideSpeciesInfoLoader(repository: RestApiSpeciesRepository): SpeciesInfoLoader {
        return SpeciesInfoLoader(repository)
    }

    @Provides
    fun provideFilmInfoLoader(repository: RestApiFilmRepository): FilmInfoLoader {
        return FilmInfoLoader(repository)
    }

}