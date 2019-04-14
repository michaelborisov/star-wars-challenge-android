package michaelborisov.starwarschallenge.character.detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import michaelborisov.starwarschallenge.Character
import michaelborisov.starwarschallenge.Film
import michaelborisov.starwarschallenge.R


class FilmDetailAdapter() :
    RecyclerView.Adapter<FilmDetailAdapter.FilmDetailViewHolder>() {

    private var films = ArrayList<Film>()

    private lateinit var clickEvent: (Film) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmDetailViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(
            R.layout.character_detail_film_item, parent, false
        )
        contactView.setOnClickListener {

        }

        return FilmDetailViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmDetailViewHolder, position: Int) {
        val film = films[position]
        holder.filmTitle.text = film.title
        holder.filmDate.text = film.release_date
        holder.openingCrawl.text = "${film.opening_crawl.substring(0, 300)}..."
//        holder.itemView.setOnClickListener {
//            clickEvent(film)
//        }
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