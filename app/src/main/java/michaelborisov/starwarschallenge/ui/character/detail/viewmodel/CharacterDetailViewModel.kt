package michaelborisov.starwarschallenge.ui.character.detail.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import michaelborisov.starwarschallenge.datamodel.Film

class CharacterDetailViewModel : ViewModel() {

    val characterName = MutableLiveData<String>()
    val characterBirthYear = MutableLiveData<String>()
    val characterHeight = MutableLiveData<String>()
    val speciesName = MutableLiveData<String>()
    val languages = MutableLiveData<String>()
    val planetNames = MutableLiveData<List<String>>()
    val planetPopulations = MutableLiveData<List<String>>()

    val filmDetails = MutableLiveData<List<Film>>()
}
