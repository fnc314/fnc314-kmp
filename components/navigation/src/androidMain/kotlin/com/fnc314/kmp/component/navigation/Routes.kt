package com.fnc314.kmp.component.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    data object Home : Routes()
}