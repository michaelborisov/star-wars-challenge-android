package michaelborisov.starwarschallenge.repository.other.species

import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.repository.other.common.StarWarsUniverseRepository

/**
 * [SpeciesRepository] - should be used for implementation of all [Species] repositories
 */
interface SpeciesRepository :
    StarWarsUniverseRepository<Species> {
}