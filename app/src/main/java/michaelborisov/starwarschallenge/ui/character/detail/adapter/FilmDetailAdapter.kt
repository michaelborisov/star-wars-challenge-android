package michaelborisov.starwarschallenge.ui.character.detail.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.ui.character.detail.view.CharacterDetailFragment

/**
 * Adapter class for RecyclerView, used on [CharacterDetailFragment]
 * to display films with the character
 */
class FilmDetailAdapter :
    RecyclerView.Adapter<FilmDetailAdapter.FilmDetailViewHolder>() {

    private var films = ArrayList<Film>()

    private lateinit var clickEvent: (Film) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmDetailViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val filmItemView = inflater.inflate(
            R.layout.character_detail_film_item, parent, false
        )

        return FilmDetailViewHolder(filmItemView)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FilmDetailViewHolder, position: Int) {
        val film = films[position]

        holder.filmTitle.text = film.title
        holder.filmDate.text = film.release_date

        //Shortening opening crawl for better displaying.
        holder.openingCrawl.text = "${film.opening_crawl.substring(0, 300)}..."

        holder.itemView.setOnClickListener { clickEvent(film) }
    }

    class FilmDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filmTitle: TextView = itemView.findViewById(R.id.tvFilmTitle)
        var filmDate: TextView = itemView.findViewById(R.id.tvFilmDate)
        var openingCrawl: TextView = itemView.findViewById(R.id.tvOpeningCrawl)
    }

    fun replaceEntries(newFilms: ArrayList<Film>) {
        this.films = newFilms
        notifyDataSetChanged()
    }

    fun setOnFilmClickListener(emitClickEvent: (Film) -> Unit) {
        this.clickEvent = emitClickEvent
    }
}