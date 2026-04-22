package com.fnc314.kmp.app.compose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform