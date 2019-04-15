package michaelborisov.starwarschallenge.ui.character.search.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import michaelborisov.starwarschallenge.ui.character.search.domain.LoadCharacters
import michaelborisov.starwarschallenge.ui.character.search.view.CharacterSearchView
import michaelborisov.starwarschallenge.ui.character.search.viewmodel.CharacterSearchViewModel
import michaelborisov.starwarschallenge.network.RestStarWarsApiHelper
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

    @Inject
    lateinit var apiHelper: RestStarWarsApiHelper

    @Inject
    lateinit var presenterConfig: PresenterConfig

    private var lastQuery = "-1"

    private lateinit var viewModel: CharacterSearchViewModel

    override fun onAttachView(view: CharacterSearchView) {
        super.onAttachView(view)
        viewModel = view.viewModel

        subscribeToUiEvents(view)
    }

    private fun subscribeToUiEvents(view: CharacterSearchView) {
        handler.manageViewDisposable(view.getSearchQueryObservable()
            .debounce(presenterConfig.textInputChangeDebounce, TimeUnit.MILLISECONDS)
            .filter { lastQuery != it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.toggleLoadingAndRecyclerViewVisibility(false) }
            .doOnNext { lastQuery = it.toString() }
            .observeOn(Schedulers.computation())
            .flatMap {
                LoadCharacters(apiHelper).execute(it.toString()).toObservable()
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