package com.fnc314.kmp.app.compose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "fnc314-kmp",
    ) {
        App()
    }
}