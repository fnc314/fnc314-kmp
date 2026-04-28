package com.fnc314.kmp.tools.gradleconfigs

object GradleConfigs {
  object Versions {
    object Kotlin {
      const val VERSION: String = "2.3.21"
    }

    object Android {
      object Sdk {
        const val COMPILE: Int = 37
        const val TARGET: Int = 37
        const val MIN: Int = 24
      }
      object BuildTools {
        const val VERSION: String = "37.0.0"
      }
    }
  }
}