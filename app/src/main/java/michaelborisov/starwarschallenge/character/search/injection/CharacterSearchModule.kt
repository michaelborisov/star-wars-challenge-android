package michaelborisov.starwarschallenge.character.search.injection

import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import michaelborisov.starwarschallenge.character.search.presenter.CharacterSearchPresenter

@Module
class CharacterSearchModule {

    @Provides
    fun providePresenter(injector: MembersInjector<CharacterSearchPresenter>): CharacterSearchPresenter {
        val presenter = CharacterSearchPresenter()
        injector.injectMembers(presenter)
        return presenter
    }
}