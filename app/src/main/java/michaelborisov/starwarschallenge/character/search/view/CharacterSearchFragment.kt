package michaelborisov.starwarschallenge.character.search.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.character_search_fragment.*
import michaelborisov.starwarschallenge.Character

import michaelborisov.starwarschallenge.character.search.adapter.CharacterSearchResultAdapter
import michaelborisov.starwarschallenge.character.search.injection.DaggerCharacterSearchComponent
import michaelborisov.starwarschallenge.character.search.presenter.CharacterSearchPresenter
import michaelborisov.starwarschallenge.character.search.viewmodel.CharacterSearchViewModel
import net.grandcentrix.thirtyinch.TiFragment
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.character.detail.view.CharacterDetailFragment


class CharacterSearchFragment :
    TiFragment<CharacterSearchPresenter, CharacterSearchView>(),
    CharacterSearchView {

    private val characterSearchFragment = "CharacterSearchFragment"
    override fun showCharacterDetailFragment(character: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, CharacterDetailFragment.newInstance(character))
            ?.addToBackStack(characterSearchFragment)
            ?.commit()
    }

    private var onCharacterClickPublishRelay = PublishRelay.create<Character>()
    override fun getOnCharacterClickObservable(): Observable<Character> {
        return onCharacterClickPublishRelay
    }

    @Inject
    lateinit var characterSearchPresenter: CharacterSearchPresenter


    private val foundCharactersAdapter = CharacterSearchResultAdapter()

    override fun getSearchQueryObservable(): Observable<CharSequence> {
        return RxTextView.textChanges(etSearchQuery)
    }

    override fun providePresenter(): CharacterSearchPresenter {
        return characterSearchPresenter
    }

    companion object {
        fun newInstance() = CharacterSearchFragment()
    }

    override lateinit var viewModel: CharacterSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity);
        rvSearchResults.layoutManager = layoutManager;
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
        // TODO: Use the ViewModel
        viewModel.characters.observe(this, Observer {
            foundCharactersAdapter.replaceEntries(it as ArrayList<Character>)
        })
    }


    private fun initializeInjector() {
        val component = DaggerCharacterSearchComponent.builder()
            .build()

        component.inject(this)
    }


}
