package michaelborisov.starwarschallenge.character.detail.injection

import dagger.Component
import michaelborisov.starwarschallenge.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.character.detail.view.CharacterDetailFragment
import michaelborisov.starwarschallenge.character.search.injection.CharacterDetailModule
import michaelborisov.starwarschallenge.character.search.presenter.CharacterSearchPresenter
import michaelborisov.starwarschallenge.character.search.view.CharacterSearchFragment

@Component(modules = [CharacterDetailModule::class])
interface CharacterDetailComponent {

    fun inject(presenter: CharacterDetailPresenter)

    fun inject(fragment: CharacterDetailFragment)
}