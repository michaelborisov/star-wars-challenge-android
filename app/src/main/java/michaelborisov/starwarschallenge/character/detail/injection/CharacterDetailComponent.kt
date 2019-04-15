package michaelborisov.starwarschallenge.character.detail.injection

import dagger.Component
import michaelborisov.starwarschallenge.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.character.detail.view.CharacterDetailFragment

@Component(modules = [CharacterDetailModule::class])
interface CharacterDetailComponent {

    fun inject(presenter: CharacterDetailPresenter)

    fun inject(fragment: CharacterDetailFragment)
}