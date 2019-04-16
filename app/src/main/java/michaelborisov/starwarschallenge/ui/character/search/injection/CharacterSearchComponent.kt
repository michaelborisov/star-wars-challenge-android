package michaelborisov.starwarschallenge.ui.character.search.injection

import dagger.Component
import michaelborisov.starwarschallenge.ui.character.search.presenter.CharacterSearchPresenter
import michaelborisov.starwarschallenge.ui.character.search.view.CharacterSearchFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CharacterSearchModule::class])
interface CharacterSearchComponent {

    fun inject(presenter: CharacterSearchPresenter)

    fun inject(fragment: CharacterSearchFragment)
}