package michaelborisov.starwarschallenge.character.search.injection

import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import michaelborisov.starwarschallenge.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.character.search.presenter.CharacterSearchPresenter

@Module
class CharacterDetailModule {

    @Provides
    fun providePresenter(injector: MembersInjector<CharacterDetailPresenter>): CharacterDetailPresenter {
        val presenter = CharacterDetailPresenter()
        injector.injectMembers(presenter)
        return presenter
    }
}