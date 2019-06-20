package michaelborisov.starwarschallenge.ui.character.search.injection

import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import michaelborisov.starwarschallenge.repository.character.RestApiCharacterRepository
import michaelborisov.starwarschallenge.ui.character.search.domain.CharacterLoader
import michaelborisov.starwarschallenge.ui.character.search.presenter.CharacterSearchPresenter

@Module
class CharacterSearchModule {

    @Provides
    fun providePresenter(injector: MembersInjector<CharacterSearchPresenter>): CharacterSearchPresenter {
        val presenter = CharacterSearchPresenter()
        injector.injectMembers(presenter)
        return presenter
    }

    @Provides
    fun provideCharacterLoader(repository: RestApiCharacterRepository): CharacterLoader {
        return CharacterLoader(repository)
    }
}