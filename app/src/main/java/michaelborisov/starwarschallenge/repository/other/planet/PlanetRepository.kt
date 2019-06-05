package michaelborisov.starwarschallenge.repository.other.planet

import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepository

/**
 * [PlanetRepository] - should be used for implementation of all [Planet] repositories
 */
interface PlanetRepository : StarWarsUniverseRepository<Planet>