package michaelborisov.starwarschallenge.ui.character.detail.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.character_detail_fragment.*
import michaelborisov.starwarschallenge.datamodel.Character
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.ui.character.detail.adapter.FilmDetailAdapter
import michaelborisov.starwarschallenge.ui.character.detail.injection.DaggerCharacterDetailComponent
import michaelborisov.starwarschallenge.ui.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.ui.character.detail.viewmodel.CharacterDetailViewModel
import net.grandcentrix.thirtyinch.TiFragment
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread
import javax.inject.Inject


class CharacterDetailFragment : TiFragment<CharacterDetailPresenter, CharacterDetailView>(),
    CharacterDetailView {
    override fun getFilmCategoryUrl(): String {
        return getString(R.string.film_category_url)
    }

    override fun getSpeciesCategoryUrl(): String {
        return getString(R.string.species_category_url)
    }

    override fun getPlanetCategoryUrl(): String {
        return getString(R.string.planet_category_url)
    }

    override lateinit var viewModel: CharacterDetailViewModel

    @Inject
    lateinit var presenter: CharacterDetailPresenter

    lateinit var character: Character

    private val filmItemsAdapter = FilmDetailAdapter()

    private var onFilmClickPublishRelay = PublishRelay.create<Film>()

    override fun getOnFilmClickObservable(): Observable<Film> {
        return onFilmClickPublishRelay
    }

    override fun providePresenter(): CharacterDetailPresenter {
        return presenter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        initializeInjector()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.character_detail_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvDetailFilms.layoutManager = layoutManager
        filmItemsAdapter.setOnFilmClickListener(onFilmClickPublishRelay::accept)
        rvDetailFilms.adapter = filmItemsAdapter
        val dividerItemDecoration = DividerItemDecoration(
            rvDetailFilms.context,
            layoutManager.orientation
        )
        rvDetailFilms.addItemDecoration(dividerItemDecoration)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailViewModel::class.java)
        viewModel.character = character

        subscribeToUiUpdates()
    }

    private fun subscribeToUiUpdates() {
        viewModel.charaterName.observe(this, Observer {
            tvDetailCharacterName.text = it
        })

        viewModel.characterBirthYear.observe(this, Observer {
            tvDetailCharacterBirthDate.text = it
        })

        viewModel.characterHeight.observe(this, Observer {
            tvDetailHeight.text = it
        })

        viewModel.speciesName.observe(this, Observer {
            tvDetailSpeciesName.text = it
        })

        viewModel.languages.observe(this, Observer {
            tvDetailLanguages.text = it
        })

        viewModel.planetNames.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                tvDetailPlanet1.text = it[0]
                if (it.size > 1) {
                    planetBlock2.visibility = View.VISIBLE
                    tvDetailPlanet2.text = it[1]
                }
            } else {
                tvDetailPlanet1.text = getString(R.string.not_applicable)
            }

        })

        viewModel.planetPopulations.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                tvDetailPopulation1.text = it[0]
                if (it.size > 1) {
                    planetBlock2.visibility = View.VISIBLE
                    tvDetailPopulation2.text = it[1]
                }
            } else {
                tvDetailPopulation1.text = getString(R.string.not_applicable)
            }

        })

        viewModel.languages.observe(this, Observer {
            tvDetailLanguages.text = it
        })

        viewModel.filmDetails.observe(this, Observer {
            filmItemsAdapter.replaceEntries(it as ArrayList<Film>)
        })
    }

    private val filmDetailDialogTag = "FilmDetailDialogTag"

    override fun openFilmDetailDialog(film: Film) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        val previousFragment = activity?.supportFragmentManager?.findFragmentByTag(filmDetailDialogTag)
        if (previousFragment != null) {
            fragmentTransaction?.remove(previousFragment)
        }
        fragmentTransaction?.addToBackStack(null)
        val filmDialogFragment = FilmDetailDialogFragment.newInstance(film)
        filmDialogFragment.show(fragmentTransaction, filmDetailDialogTag)
    }

    companion object {
        fun newInstance(character: Character): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            fragment.character = character
            return fragment
        }
    }

    @CallOnMainThread
    override fun showErrorToast() {
        Toast.makeText(activity, R.string.something_went_wrong, Toast.LENGTH_LONG)
            .show()
    }

    @CallOnMainThread
    override fun setActivityTitle(name: String) {
        activity?.title = "${getString(R.string.details_text)} $name"
    }

    @CallOnMainThread
    override fun toggleNothingFoundTextVisibility(isVisible: Boolean) {
        if (isVisible) {
            tvDetailNothingPlaceHolder.visibility = View.VISIBLE
            rvDetailFilms.visibility = View.GONE
            tvDetailLoadingPlaceHolder.visibility = View.GONE
        } else {
            rvDetailFilms.visibility = View.VISIBLE
            tvDetailNothingPlaceHolder.visibility = View.GONE
        }
    }

    @CallOnMainThread
    override fun toggleLoadingAndRecyclerViewVisibility(isRecyclerVisible: Boolean) {
        if (isRecyclerVisible) {
            rvDetailFilms.visibility = View.VISIBLE
            tvDetailLoadingPlaceHolder.visibility = View.GONE
            tvDetailNothingPlaceHolder.visibility = View.GONE
        } else {
            tvDetailLoadingPlaceHolder.visibility = View.VISIBLE
            rvDetailFilms.visibility = View.GONE
            tvDetailNothingPlaceHolder.visibility = View.GONE
        }
    }

    private fun initializeInjector() {
        val component = DaggerCharacterDetailComponent.builder()
            .build()

        component.inject(this)
    }

}
