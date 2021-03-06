package michaelborisov.starwarschallenge.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresenterConfig @Inject constructor() {

    /**
     * Debounce timeout for clicks
     */
    val clickDebounce = 300L

    /**
     * Debounce timeout for change of the text in the Input.
     */
    val textInputChangeDebounce = 400L


}