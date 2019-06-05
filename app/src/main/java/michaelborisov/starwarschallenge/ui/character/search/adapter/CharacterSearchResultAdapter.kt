package michaelborisov.starwarschallenge.ui.character.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.datamodel.Character

/**
 * Adapter for RecyclerView used to display results of search of [Character]s
 */
class CharacterSearchResultAdapter :
    RecyclerView.Adapter<CharacterSearchResultAdapter.CharacterSearchResultViewHolder>() {

    private var foundCharacters = ArrayList<Character>()

    private lateinit var clickEvent: (Character) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSearchResultViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val characterView = inflater.inflate(
            R.layout.character_search_result_item, parent, false
        )

        return CharacterSearchResultViewHolder(characterView)
    }

    override fun getItemCount(): Int {
        return foundCharacters.size
    }

    override fun onBindViewHolder(holder: CharacterSearchResultViewHolder, position: Int) {
        val character = foundCharacters[position]
        holder.characterName.text = character.name
        holder.characterBirthYear.text = character.birth_year
        holder.itemView.setOnClickListener {
            clickEvent(character)
        }
    }

    class CharacterSearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var characterName: TextView = itemView.findViewById(R.id.tvCharacterName)
        var characterBirthYear: TextView = itemView.findViewById(R.id.tvCharacterBirthDate)

    }

    fun replaceEntries(newCharacters: ArrayList<Character>) {
        this.foundCharacters = newCharacters
        notifyDataSetChanged()
    }

    fun setOnCharacterClickListener(emitClickEvent: (Character) -> Unit) {
        this.clickEvent = emitClickEvent
    }
}