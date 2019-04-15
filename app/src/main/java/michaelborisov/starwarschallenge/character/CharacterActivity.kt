package michaelborisov.starwarschallenge.character

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.character.search.view.CharacterSearchFragment


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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


}
