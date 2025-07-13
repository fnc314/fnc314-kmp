package com.fnc314.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform