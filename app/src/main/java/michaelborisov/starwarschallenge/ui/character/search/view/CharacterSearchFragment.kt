package michaelborisov.starwarschallenge.ui.character.search.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.character_search_fragment.*
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.ui.character.detail.view.CharacterDetailFragment
import michaelborisov.starwarschallenge.ui.character.search.adapter.CharacterSearchResultAdapter
import michaelborisov.starwarschallenge.ui.character.search.injection.DaggerCharacterSearchComponent
import michaelborisov.starwarschallenge.ui.character.search.presenter.CharacterSearchPresenter
import michaelborisov.starwarschallenge.ui.character.search.viewmodel.CharacterSearchViewModel
import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread
import javax.inject.Inject


class CharacterSearchFragment :
    TiFragment<CharacterSearchPresenter, CharacterSearchView>(),
    CharacterSearchView {

    override lateinit var viewModel: CharacterSearchViewModel

    @Inject
    lateinit var characterSearchPresenter: CharacterSearchPresenter

    private val foundCharactersAdapter = CharacterSearchResultAdapter()

    private var onCharacterClickPublishRelay = PublishRelay.create<Character>()

    override fun getOnCharacterClickObservable(): Observable<Character> {
        return onCharacterClickPublishRelay
    }

    override fun getSearchQueryObservable(): Observable<CharSequence> {
        return RxTextView.textChanges(etSearchQuery)
    }

    override fun providePresenter(): CharacterSearchPresenter {
        return characterSearchPresenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initializeInjector()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foundCharactersAdapter.setOnCharacterClickListener(onCharacterClickPublishRelay::accept)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.character_search_fragment, container, false)
    }

    @CallOnMainThread
    override fun showErrorToast() {
        Toast.makeText(activity, R.string.something_went_wrong, Toast.LENGTH_LONG)
            .show()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.character_search_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        rvSearchResults.layoutManager = layoutManager
        rvSearchResults.adapter = foundCharactersAdapter
        val dividerItemDecoration = DividerItemDecoration(
            rvSearchResults.context,
            layoutManager.orientation
        )
        rvSearchResults.addItemDecoration(dividerItemDecoration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterSearchViewModel::class.java)
        viewModel.characters.observe(this, Observer {
            foundCharactersAdapter.replaceEntries(it as ArrayList<Character>)
        })
    }

    companion object {
        fun newInstance() = CharacterSearchFragment()
    }

    private val characterSearchFragment = "CharacterSearchFragment"

    override fun showCharacterDetailFragment(character: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, CharacterDetailFragment.newInstance(character))
            ?.addToBackStack(characterSearchFragment)
            ?.commit()
    }

    @CallOnMainThread
    override fun toggleNothingFoundTextVisibility(isVisible: Boolean) {
        if (isVisible) {
            tvNothingPlaceHolder.visibility = View.VISIBLE
            rvSearchResults.visibility = View.GONE
            tvLoadingPlaceHolder.visibility = View.GONE
        } else {
            rvSearchResults.visibility = View.VISIBLE
            tvNothingPlaceHolder.visibility = View.GONE
        }
    }

    @CallOnMainThread
    override fun toggleLoadingAndRecyclerViewVisibility(isRecyclerVisible: Boolean) {
        if (isRecyclerVisible) {
            rvSearchResults.visibility = View.VISIBLE
            tvLoadingPlaceHolder.visibility = View.GONE
            tvNothingPlaceHolder.visibility = View.GONE
        } else {
            tvLoadingPlaceHolder.visibility = View.VISIBLE
            rvSearchResults.visibility = View.GONE
            tvNothingPlaceHolder.visibility = View.GONE
        }
    }

    override fun hideSoftKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun initializeInjector() {
        val component = DaggerCharacterSearchComponent.builder()
            .build()

        component.inject(this)
    }


}
