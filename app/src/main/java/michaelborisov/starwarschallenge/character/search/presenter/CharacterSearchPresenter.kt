package michaelborisov.starwarschallenge.character.search.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import michaelborisov.starwarschallenge.character.search.domain.LoadCharacters
import michaelborisov.starwarschallenge.character.search.view.CharacterSearchView
import michaelborisov.starwarschallenge.character.search.viewmodel.CharacterSearchViewModel
import michaelborisov.starwarschallenge.datamanagement.RestStarWarsApiHelper
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

    lateinit var viewModel: CharacterSearchViewModel
    override fun onAttachView(view: CharacterSearchView) {
        super.onAttachView(view)
        viewModel = view.viewModel

        subscribeToUiEvents(view)
    }

    private fun subscribeToUiEvents(view: CharacterSearchView) {
        handler.manageViewDisposable(view.getSearchQueryObservable()
            .debounce(300, TimeUnit.MILLISECONDS)
            //.filter { query -> query.length > 1 }
            .observeOn(Schedulers.computation())
            .flatMap {
                LoadCharacters(apiHelper).execute(it.toString()).toObservable()
            }
            .subscribe {
                viewModel.characters.postValue(it)
            }

        )

        handler.manageViewDisposable(view.getOnCharacterClickObservable()
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showCharacterDetailFragment(it)
            }
        )
    }
}