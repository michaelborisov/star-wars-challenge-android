package michaelborisov.starwarschallenge.character.detail.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.R


class FilmDetailDialogFragment : DialogFragment() {

    private lateinit var film: Film

    private lateinit var filmTitle: TextView
    private lateinit var filmDate: TextView
    private lateinit var openingCrawl: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.character_detail_film_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmTitle = view.findViewById(R.id.tvFilmTitle)
        filmDate = view.findViewById(R.id.tvFilmDate)
        openingCrawl = view.findViewById(R.id.tvOpeningCrawl)

        filmTitle.text = film.title
        filmDate.text = film.release_date
        openingCrawl.text = film.opening_crawl

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