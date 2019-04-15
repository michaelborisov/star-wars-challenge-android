package michaelborisov.starwarschallenge.ui.character.detail.injection

import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import michaelborisov.starwarschallenge.ui.character.detail.presenter.CharacterDetailPresenter

@Module
class CharacterDetailModule {

    @Provides
    fun providePresenter(injector: MembersInjector<CharacterDetailPresenter>): CharacterDetailPresenter {
        val presenter = CharacterDetailPresenter()
        injector.injectMembers(presenter)
        return presenter
    }
}