package michaelborisov.starwarschallenge.ui.character.detail.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.ui.character.detail.domain.FilmInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.domain.PlanetInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.domain.SpeciesInfoLoader
import michaelborisov.starwarschallenge.ui.character.detail.view.CharacterDetailView
import michaelborisov.starwarschallenge.ui.character.detail.viewmodel.CharacterDetailViewModel
import michaelborisov.starwarschallenge.utils.*
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Presenter which survives
 */
class CharacterDetailPresenter : TiPresenter<CharacterDetailView>() {

    /**
     * Helper class, which disposes [io.reactivex.disposables.Disposable] from the view
     * or other [Observable]s.
     */
    private val handler = RxTiPresenterDisposableHandler(this)

    /**
     * PresenterConfig to unify behaviour of presenters across the app.
     */
    @Inject
    lateinit var presenterConfig: PresenterConfig

    lateinit var currentCharacter: Character

    private lateinit var viewModel: CharacterDetailViewModel

    /**
     * Block of information loaders, required on the page.
     */
    @Inject
    lateinit var filmInfoLoader: FilmInfoLoader

    @Inject
    lateinit var planetInfoLoader: PlanetInfoLoader

    @Inject
    lateinit var speciesInfoLoader: SpeciesInfoLoader

    override fun onAttachView(view: CharacterDetailView) {
        super.onAttachView(view)
        viewModel = view.viewModel

        loadSpeciesInfo(view, currentCharacter)
        loadFilmInfo(view, currentCharacter)

        updateUiElements()
        view.setActivityTitle(currentCharacter.name)
        subscribeToUiEvents(view)
    }

    /**
     * Submit values which are already available to the View via ViewModel.
     */
    private fun updateUiElements() {
        viewModel.characterName.postValue(currentCharacter.name)
        viewModel.characterBirthYear.postValue(currentCharacter.birth_year)
        viewModel.characterHeight.postValue(constructCharacterHeightStringValue(currentCharacter))
    }

    private fun loadFilmInfo(view: CharacterDetailView, character: Character) {
        handler.manageViewDisposable(
            filmInfoLoader
                .execute(character.films)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.toggleLoadingAndRecyclerViewVisibility(true) }
                .subscribe({
                    viewModel.filmDetails.postValue(it)
                    if (it.isEmpty()) {
                        view.toggleNothingFoundTextVisibility(true)
                    } else {
                        view.toggleNothingFoundTextVisibility(false)
                    }
                }, { e ->
                    view.showErrorToast()
                    e.printStackTrace()
                })
        )
    }

    private fun loadSpeciesInfo(view: CharacterDetailView, character: Character) {
        handler.manageViewDisposable(
            speciesInfoLoader
                .execute(character.species)
                .doOnSuccess {
                    viewModel.speciesName.postValue(constructCharacterSpecies(it))
                    viewModel.languages.postValue(constructCharacterLanguages(it))
                }
                .flatMap {
                    val homeworlds = mutableListOf<String>()
                    it.forEach { species ->
                        if (species.homeworld != null) {
                            homeworlds.add(species.homeworld)
                        }
                    }
                    planetInfoLoader.execute(homeworlds)
                }
                .subscribe({
                    viewModel.planetNames.postValue(constructPlanetNamesInfo(it))
                    viewModel.planetPopulations.postValue(constructPlanetPopulationsInfo(it))
                }, { e ->
                    view.showErrorToast()
                    e.printStackTrace()
                })
        )
    }

    private fun subscribeToUiEvents(view: CharacterDetailView) {
        handler.manageViewDisposable(
            view.getOnFilmClickObservable()
                .debounce(presenterConfig.clickDebounce, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.openFilmDetailDialog(it)
                }, { e ->
                    view.showErrorToast()
                    e.printStackTrace()
                })
        )
    }

    /**
     * Block of helper methods for formatting for the View
     */
    private fun constructCharacterHeightStringValue(character: Character): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(character.height).append(cmLabel)
        stringBuilder.append(
            String.format("%.1f", character.height.toFloat() / cmToFeet)
        ).append(feetLabel)
        stringBuilder.append(
            String.format("%.1f", character.height.toFloat() / cmToInches)
        ).append(inchesLabel)
        return stringBuilder.toString()
    }

    private fun constructCharacterSpecies(species: List<Species>): String {
        val stringBuilderSpeciesName = StringBuilder()
        species.forEach {
            stringBuilderSpeciesName.append(it.name).append(comma)
        }
        return stringBuilderSpeciesName.removeSuffix(comma).toString()
    }

    private fun constructCharacterLanguages(species: List<Species>): String {
        val stringBuilderLanguage = StringBuilder()
        species.forEach {
            stringBuilderLanguage.append(it.language).append(comma)
        }
        return stringBuilderLanguage.removeSuffix(comma).toString()
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
}
