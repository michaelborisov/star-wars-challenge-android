package michaelborisov.starwarschallenge.ui.character.detail.domain

import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.repository.other.planet.PlanetRepository

/**
 * Class, providing functionality for loading information about [Planet].
 */
class PlanetInfoLoader(planetRepository: PlanetRepository) : InfoLoader<Planet>(planetRepository)