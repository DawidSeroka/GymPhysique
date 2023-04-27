package com.myproject.gymphysqiue.core.domain.measure

import com.juul.kable.Advertisement
import javax.inject.Inject

class ValidateCurrentAdvertisementsUseCase @Inject constructor() : (Advertisement, List<Advertisement>) -> List<Advertisement> {
    override fun invoke(newAdvertisement: Advertisement, oldAdvertisements: List<Advertisement>): List<Advertisement> {
        val advExists =
            oldAdvertisements.any { it.peripheralName == newAdvertisement.peripheralName }
        return if (!advExists) {
            oldAdvertisements + newAdvertisement
        } else {
            oldAdvertisements
        }
    }
}
