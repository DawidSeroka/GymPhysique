package com.myproject.gymphysqiue.core.domain.measure

import com.juul.kable.Advertisement
import timber.log.Timber
import javax.inject.Inject

class ValidateCurrentAdvertisementsUseCase @Inject constructor() : (Advertisement, List<Advertisement>) -> List<Advertisement> {
    override fun invoke(newAdvertisement: Advertisement, oldAdvertisements: List<Advertisement>): List<Advertisement> {
        val advExists =
            oldAdvertisements.any { it.address == newAdvertisement.address }
        return if (!advExists) {
            oldAdvertisements + newAdvertisement
        } else {
            oldAdvertisements
        }
    }
}
