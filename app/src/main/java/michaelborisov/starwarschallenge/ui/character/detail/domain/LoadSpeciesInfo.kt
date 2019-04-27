package michaelborisov.starwarschallenge.ui.character.detail.domain

import michaelborisov.starwarschallenge.datamodel.Planet
import michaelborisov.starwarschallenge.datamodel.Species
import michaelborisov.starwarschallenge.repository.other.species.SpeciesRepository

/**
 * Class, providing functionality for loading information about [Planet].
 */
class LoadSpeciesInfo(speciesRepository: SpeciesRepository) : LoadInfo<Species>(speciesRepository) {

}