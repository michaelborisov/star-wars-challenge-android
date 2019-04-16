package michaelborisov.starwarschallenge.ui.character.search.view

import io.reactivex.Observable
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.ui.character.search.viewmodel.CharacterSearchViewModel
import net.grandcentrix.thirtyinch.TiView

interface CharacterSearchView : TiView {

    val viewModel: CharacterSearchViewModel

    /**
     * Provides observable for searchQuery
     */
    fun getSearchQueryObservable(): Observable<CharSequence>

    /**
     * Provides observable for [Character] clicks
     */
    fun getOnCharacterClickObservable(): Observable<Character>

    /**
     * Shows new character detail fragment.
     */
    fun showCharacterDetailFragment(character: Character)

    fun toggleLoadingAndRecyclerViewVisibility(isRecyclerVisible: Boolean)

    fun toggleNothingFoundTextVisibility(isVisible: Boolean)

    fun hideSoftKeyBoard()

    fun showErrorToast()
}