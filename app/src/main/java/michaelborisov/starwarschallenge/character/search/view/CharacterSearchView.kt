package michaelborisov.starwarschallenge.character.search.view

import io.reactivex.Observable
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.character.search.viewmodel.CharacterSearchViewModel
import net.grandcentrix.thirtyinch.TiView

interface CharacterSearchView : TiView {

    val viewModel: CharacterSearchViewModel

    fun getSearchQueryObservable(): Observable<CharSequence>

    fun getOnCharacterClickObservable(): Observable<Character>

    fun showCharacterDetailFragment(character: Character)
}