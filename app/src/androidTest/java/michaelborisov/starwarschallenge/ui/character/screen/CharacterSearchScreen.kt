package michaelborisov.starwarschallenge.ui.character.screen

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import michaelborisov.starwarschallenge.R
import org.hamcrest.Matcher

class CharacterSearchScreen : Screen<CharacterSearchScreen>() {
    val characterSearchEditText = KEditText { withId(R.id.etSearchQuery) }
    val characterSearchResultRecyclerView: KRecyclerView = KRecyclerView({
        withId(R.id.rvSearchResults)
    }, itemTypeBuilder = {
        itemType(::FoundCharacterItem)
    })

    class FoundCharacterItem(parent: Matcher<View>) : KRecyclerItem<FoundCharacterItem>(parent) {
        val characterName: KTextView = KTextView(parent) { withId(R.id.tvCharacterName) }
    }
}