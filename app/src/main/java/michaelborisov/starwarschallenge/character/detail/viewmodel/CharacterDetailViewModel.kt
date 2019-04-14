package michaelborisov.starwarschallenge.character.detail.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Film

class CharacterDetailViewModel : ViewModel() {

    lateinit var character: Character

    val charaterName = MutableLiveData<String>()
    val characterBirthYear = MutableLiveData<String>()
    val characterHeight = MutableLiveData<String>()
    val speciesName = MutableLiveData<String>()
    val languages = MutableLiveData<String>()
    val planetNames = MutableLiveData<List<String>>()
    val planetPopulations = MutableLiveData<List<String>>()

    val filmDetails = MutableLiveData<List<Film>>()
}
