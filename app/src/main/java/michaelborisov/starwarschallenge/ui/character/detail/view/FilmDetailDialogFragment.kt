package michaelborisov.starwarschallenge.ui.character.detail.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.datamodel.Film


/**
 * DialogFragment to display full information about film.
 */
class FilmDetailDialogFragment : DialogFragment() {

    private lateinit var film: Film

    private lateinit var filmTitle: TextView
    private lateinit var filmDate: TextView
    private lateinit var openingCrawl: TextView

    private val filmTitleKey = "filmTitle"
    private val filmDateKey = "filmDate"
    private val openingCrawlKey = "openingCrawl"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            film = loadFilmInfoFromBundle(savedInstanceState)
        }
        return inflater.inflate(R.layout.character_detail_film_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmTitle = view.findViewById(R.id.tvFilmTitle)
        filmDate = view.findViewById(R.id.tvFilmDate)
        openingCrawl = view.findViewById(R.id.tvOpeningCrawl)

        if (::film.isInitialized) {
            filmTitle.text = film.title
            filmDate.text = film.release_date
            openingCrawl.text = film.opening_crawl
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveFilmInfoIntoBundle(film, outState)
    }

    private fun saveFilmInfoIntoBundle(film: Film, outState: Bundle) {
        outState.putString(filmTitleKey, film.title)
        outState.putString(filmDateKey, film.release_date)
        outState.putString(openingCrawlKey, film.opening_crawl)
    }

    private fun loadFilmInfoFromBundle(savedInstanceState: Bundle): Film {
        film = Film(
            savedInstanceState.getString(filmTitleKey),
            savedInstanceState.getString(filmDateKey),
            savedInstanceState.getString(openingCrawlKey)
        )
        return film
    }

    companion object {
        fun newInstance(film: Film): FilmDetailDialogFragment {
            val filmDetailDialogFragment =
                FilmDetailDialogFragment()
            filmDetailDialogFragment.film = film
            return filmDetailDialogFragment
        }
    }
}