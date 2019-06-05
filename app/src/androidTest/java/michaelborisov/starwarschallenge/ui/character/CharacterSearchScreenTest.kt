package michaelborisov.starwarschallenge.ui.character


import android.content.pm.ActivityInfo
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.idle
import michaelborisov.starwarschallenge.R
import michaelborisov.starwarschallenge.ui.character.screen.CharacterSearchScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CharacterSearchScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(CharacterActivity::class.java)

    private val screen = CharacterSearchScreen()

    @Test
    fun testSearchHintPresence() {
        screen {
            characterSearchEditText.hasHint(R.string.search_query_hint)
        }
    }

    @Test
    fun testFirstDefaultFoundCharacterIsLuke() {
        screen {
            idle(3000)
            characterSearchResultRecyclerView {
                firstChild<CharacterSearchScreen.FoundCharacterItem> {
                    characterName.hasText("Luke Skywalker")
                }
            }
        }
    }

    @Test
    fun testR2D2FoundCharacter() {
        screen {
            idle(1000)
            characterSearchEditText {
                typeText("R2-D2")
            }
            idle(3000)
            characterSearchResultRecyclerView {
                firstChild<CharacterSearchScreen.FoundCharacterItem> {
                    characterName.hasText("R2-D2")
                }
            }
        }
    }

    @Test
    fun testR2D2FoundCharacterWithScreenRotation() {
        screen {
            idle(1000)
            characterSearchEditText {
                typeText("R2-D2")
            }
            idle(3000)
            mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            characterSearchResultRecyclerView {
                firstChild<CharacterSearchScreen.FoundCharacterItem> {
                    characterName.hasText("R2-D2")
                }
            }
        }
    }
}
