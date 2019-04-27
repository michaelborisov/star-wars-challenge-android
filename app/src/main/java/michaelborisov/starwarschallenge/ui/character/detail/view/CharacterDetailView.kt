package michaelborisov.starwarschallenge.ui.character.detail.view

import io.reactivex.Observable
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.ui.character.detail.viewmodel.CharacterDetailViewModel
import net.grandcentrix.thirtyinch.TiView


interface CharacterDetailView : TiView {

    val viewModel: CharacterDetailViewModel

    fun openFilmDetailDialog(film: Film)

    /**
     * Provides observable for clicking on the [Film]
     */
    fun getOnFilmClickObservable(): Observable<Film>

    /**
     * Toggling UI elements for improving user experience
     */
    fun toggleLoadingAndRecyclerViewVisibility(isRecyclerVisible: Boolean)

    fun toggleNothingFoundTextVisibility(isVisible: Boolean)

    fun setActivityTitle(name: String)

    fun showErrorToast()
}