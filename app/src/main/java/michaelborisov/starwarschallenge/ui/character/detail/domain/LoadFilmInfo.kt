package michaelborisov.starwarschallenge.ui.character.detail.domain

import michaelborisov.starwarschallenge.datamodel.Film
import michaelborisov.starwarschallenge.repository.other.film.FilmRepository

/**
 * Class, providing functionality for loading information about [Film].
 */
class LoadFilmInfo(filmRepository: FilmRepository) : LoadInfo<Film>(filmRepository) {

}