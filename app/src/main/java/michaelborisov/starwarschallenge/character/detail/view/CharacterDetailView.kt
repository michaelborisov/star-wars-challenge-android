package michaelborisov.starwarschallenge.character.detail.view

import io.reactivex.Observable
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.character.detail.viewmodel.CharacterDetailViewModel
import net.grandcentrix.thirtyinch.TiView


interface CharacterDetailView : TiView {

    val viewModel: CharacterDetailViewModel

    fun openFilmDetailDialog(film: Film)

    fun getOnFilmClickObservable(): Observable<Film>

    fun toggleLoadingAndRecyclerViewVisibility(isRecyclerVisible: Boolean)

    fun toggleNothingFoundTextVisibility(isVisible: Boolean)

    fun setActivityTitle(name: String)

}