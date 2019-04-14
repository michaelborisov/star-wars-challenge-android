package michaelborisov.starwarschallenge.character.detail.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.character_detail_fragment.*
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.character.detail.adapter.FilmDetailAdapter
import michaelborisov.starwarschallenge.character.detail.injection.DaggerCharacterDetailComponent
import michaelborisov.starwarschallenge.character.detail.presenter.CharacterDetailPresenter
import michaelborisov.starwarschallenge.character.detail.viewmodel.CharacterDetailViewModel
import net.grandcentrix.thirtyinch.TiFragment
import javax.inject.Inject

class CharacterDetailFragment : TiFragment<CharacterDetailPresenter, CharacterDetailView>(),
    CharacterDetailView {

    @Inject
    lateinit var presenter: CharacterDetailPresenter

    lateinit var character: Character

    private val filmItemsAdapter = FilmDetailAdapter()

    override fun providePresenter(): CharacterDetailPresenter {
        return presenter
    }

    companion object {
        fun newInstance(character: Character): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            fragment.character = character
            return fragment
        }
    }

    override lateinit var viewModel: CharacterDetailViewModel

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
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        rvDetailFilms.layoutManager = layoutManager;
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
        // TODO: Use the ViewModel
    }

    private fun initializeInjector() {
        val component = DaggerCharacterDetailComponent.builder()
            .build()

        component.inject(this)
    }

}
