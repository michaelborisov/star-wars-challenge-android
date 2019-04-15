package michaelborisov.starwarschallenge.ui.character.search.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import michaelborisov.starwarschallenge.datamodel.Character

class CharacterSearchViewModel : ViewModel() {

    val characters = MutableLiveData<List<Character>>()
}
