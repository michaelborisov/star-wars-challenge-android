package michaelborisov.starwarschallenge.ui.character.search.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import michaelborisov.starwarschallenge.network.ApiHelper
import michaelborisov.starwarschallenge.ui.character.search.domain.LoadCharacters
import michaelborisov.starwarschallenge.repository.character.RestApiCharacterRepository
import michaelborisov.starwarschallenge.ui.character.search.view.CharacterSearchView
import michaelborisov.starwarschallenge.ui.character.search.viewmodel.CharacterSearchViewModel
import michaelborisov.starwarschallenge.utils.PresenterConfig
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharacterSearchPresenter : TiPresenter<CharacterSearchView>() {

    /**
     * Helper class, which disposes [io.reactivex.disposables.Disposable] from the view
     * or other [Observable]s.
     */
    private val handler = RxTiPresenterDisposableHandler(this)

    /**
     * Injecting actual implementation of [ApiHelper], provided by module
     */
    @Inject
    lateinit var characterRepository: RestApiCharacterRepository

    /**
     * PresenterConfig to unify behaviour of presenters across the app.
     */
    @Inject
    lateinit var presenterConfig: PresenterConfig


    /**
     * Stores last search query in order not to do unnecessary search,
     * when information was just loaded on the previous step.
     */
    private var lastQuery = "-1"

    private lateinit var viewModel: CharacterSearchViewModel

    private lateinit var characterLoader: LoadCharacters

    override fun onAttachView(view: CharacterSearchView) {
        super.onAttachView(view)
        viewModel = view.viewModel

        characterLoader = LoadCharacters(characterRepository)

        subscribeToUiEvents(view)
    }

    private fun subscribeToUiEvents(view: CharacterSearchView) {
        /**
         * Handles search queries. Every time searchQuery changes - new search initialized.
         */
        handler.manageViewDisposable(view.getSearchQueryObservable()
            .debounce(presenterConfig.textInputChangeDebounce, TimeUnit.MILLISECONDS)
            // Filtering to continue only in case of change of searchQuery
            .filter { lastQuery != it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.toggleLoadingAndRecyclerViewVisibility(false) }
            .doOnNext { lastQuery = it.toString() }
            .observeOn(Schedulers.computation())
            .flatMap {
                characterLoader.execute(it.toString()).toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                viewModel.characters.postValue(it)
                view.toggleLoadingAndRecyclerViewVisibility(true)
            }
            .subscribe({

                if (it.isEmpty()) {
                    view.toggleNothingFoundTextVisibility(true)
                } else {
                    view.toggleNothingFoundTextVisibility(false)
                }
            }, { e ->
                view.showErrorToast()
                e.printStackTrace()
                view.toggleNothingFoundTextVisibility(true)
            })

        )

        /**
         * Handles clicks on found [Character] items
         */
        handler.manageViewDisposable(
            view.getOnCharacterClickObservable()
                .debounce(presenterConfig.clickDebounce, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.hideSoftKeyBoard()
                    view.showCharacterDetailFragment(it)
                }, { e ->
                    view.showErrorToast()
                    e.printStackTrace()
                })
        )
    }
}
