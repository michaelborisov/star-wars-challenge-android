package michaelborisov.starwarschallenge.ui.character

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.ui.character.detail.view.CharacterDetailFragment
import michaelborisov.starwarschallenge.ui.character.search.view.CharacterSearchFragment


/**
 * Activity, holding both [CharacterSearchFragment] and [CharacterDetailFragment].
 * Two fragments were chosen to represent workflow, because it would be easier to adapt it
 * to usage on tablets.
 */
class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_search_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterSearchFragment.newInstance())
                .commitNow()
        }
    }

    /**
     * Pop fragments from BackStack until there are some.
     * Afterwards - calls super.onBackPressed()
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


}
