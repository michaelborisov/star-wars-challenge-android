package michaelborisov.starwarschallenge.character.search.injection

import dagger.Component
import michaelborisov.starwarschallenge.character.search.presenter.CharacterSearchPresenter
import michaelborisov.starwarschallenge.character.search.view.CharacterSearchFragment

@Component(modules = [CharacterSearchModule::class])
interface CharacterSearchComponent {

    fun inject(presenter: CharacterSearchPresenter)

    fun inject(fragment: CharacterSearchFragment)
}