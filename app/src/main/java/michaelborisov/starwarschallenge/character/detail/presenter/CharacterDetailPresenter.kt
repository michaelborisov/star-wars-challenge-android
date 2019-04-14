package michaelborisov.starwarschallenge.character.detail.presenter

import io.reactivex.Observable
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Planet
import michaelborisov.starwarschallenge.Species
import michaelborisov.starwarschallenge.character.detail.domain.LoadFilmInfo
import michaelborisov.starwarschallenge.character.detail.domain.LoadPlanetInfo
import michaelborisov.starwarschallenge.character.detail.domain.LoadSpeciesInfo
import michaelborisov.starwarschallenge.character.detail.view.CharacterDetailView
import michaelborisov.starwarschallenge.character.detail.viewmodel.CharacterDetailViewModel
import michaelborisov.starwarschallenge.datamanagement.RestStarWarsApiHelper
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler
import javax.inject.Inject

class CharacterDetailPresenter : TiPresenter<CharacterDetailView>() {

    /**
     * Helper class, which disposes [io.reactivex.disposables.Disposable] from the view
     * or other [Observable]s.
     */
    private val handler = RxTiPresenterDisposableHandler(this)


    @Inject
    lateinit var apiHelper: RestStarWarsApiHelper

    lateinit var currentCharacter: Character

    lateinit var viewModel: CharacterDetailViewModel

    override fun onAttachView(view: CharacterDetailView) {
        super.onAttachView(view)
        viewModel = view.viewModel
        currentCharacter = viewModel.character

        loadSpeciesInfo(currentCharacter)
        loadFilmInfo(currentCharacter)

        updateUiElements()

        subscribeToUiEvents(view)
    }

    private fun updateUiElements() {
        viewModel.charaterName.postValue(currentCharacter.name)
        viewModel.characterBirthYear.postValue(currentCharacter.birth_year)
        viewModel.characterHeight.postValue(constructCharacterHeightStringValue(currentCharacter))
    }

    private fun constructCharacterHeightStringValue(character: Character): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(character.height).append(" cm/")
        stringBuilder.append(String.format("%.1f", character.height.toFloat() / 30.48)).append(" feet/")
        stringBuilder.append(String.format("%.1f", character.height.toFloat() / 2.54)).append(" inches")
        return stringBuilder.toString()
    }

    private fun constructCharacterSpecies(species: List<Species>): String {
        val stringBuilderSpeciesName = StringBuilder()
        species.forEach {
            stringBuilderSpeciesName.append(it.name).append(",")
        }
        return stringBuilderSpeciesName.removeSuffix(",").toString()
    }

    private fun constructCharacterLanguages(species: List<Species>): String {
        val stringBuilderLanguage = StringBuilder()
        species.forEach {
            stringBuilderLanguage.append(it.language).append(",")
        }
        return stringBuilderLanguage.removeSuffix(",").toString()
    }

    private fun constructPlanetNamesInfo(species: List<Planet>): List<String> {
        val planetNames = mutableListOf<String>()
        species.forEach {
            planetNames.add(it.name)
        }
        return planetNames
    }

    private fun constructPlanetPopulationsInfo(species: List<Planet>): List<String> {
        val planetPopulations = mutableListOf<String>()
        species.forEach {
            planetPopulations.add(it.population)
        }
        return planetPopulations
    }

    private fun loadFilmInfo(character: Character) {
        handler.manageViewDisposable(
            LoadFilmInfo(apiHelper)
                .execute(character.films)
                .subscribe { it ->
                    viewModel.filmDetails.postValue(it)
                }
        )
    }

    private fun loadSpeciesInfo(character: Character) {
        handler.manageViewDisposable(
            LoadSpeciesInfo(apiHelper)
                .execute(character.species)
                .doOnSuccess {
                    viewModel.speciesName.postValue(constructCharacterSpecies(it))
                    viewModel.languages.postValue(constructCharacterLanguages(it))
                }
                .flatMap {
                    LoadPlanetInfo(apiHelper).execute(it)
                }
                .doOnSuccess {
                    viewModel.planetNames.postValue(constructPlanetNamesInfo(it))
                    viewModel.planetPopulations.postValue(constructPlanetPopulationsInfo(it))
                }
                .subscribe { e ->

                })
    }

    private fun subscribeToUiEvents(view: CharacterDetailView) {

    }
}
