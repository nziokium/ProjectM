package com.homey.projectm.features.onboarding.domain.model

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()

    object ChangeScreens: UIEvent()
}