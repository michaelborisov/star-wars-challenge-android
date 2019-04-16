package michaelborisov.starwarschallenge.ui.character.detail.injection

import dagger.Component
import michaelborisov.starwarschallenge.ui.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.ui.character.detail.view.CharacterDetailFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CharacterDetailModule::class])
interface CharacterDetailComponent {

    fun inject(presenter: CharacterDetailPresenter)

    fun inject(fragment: CharacterDetailFragment)
}