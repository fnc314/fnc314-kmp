pluginManagement {
    includeBuild("tools")
    plugins {
        id("com.android.settings") version("8.11.1")
        id("com.android.library") version("8.11.1")
        id("com.android.application") version("8.11.1")
        id("com.android.kotlin.multiplatform.library") version("8.11.1")
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
        id("org.jetbrains.compose.hot-reload") version("1.0.0-alpha10")
        id("org.jetbrains.compose") version("1.8.2")
        id("org.jetbrains.kotlin.android") version("2.2.0")
        id("org.jetbrains.kotlin.jvm") version("2.2.0")
        id("org.jetbrains.kotlin.multiplatform") version("2.2.0")
        id("org.jetbrains.kotlin.plugin.compose") version("2.2.0")
        id("org.jetbrains.kotlin.plugin.serialization") version("2.2.0")
    }

    resolutionStrategy {
        eachPlugin {
            when {
                requested.module?.group == "org.jetbrains.kotlin" -> useVersion("2.2.0")
                requested.id.id.startsWith("com.android") -> useVersion("8.11.1")
                else -> return@eachPlugin
            }
        }
    }
}

plugins {
    id("com.fnc314.kmp.tools.gradle.configs.project-collections-settings")
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("com.android.settings") version "8.11.1"
}

android {
    compileSdk = 36
    targetSdk = 35
    minSdk = 24
    buildToolsVersion = "36.0.0"
}

projectCollections {
    registerProjectCollection("apps")
    registerProjectCollection("components")
    registerProjectCollection("design-system")
    registerNestedProjectCollection("features", 2)
}

rootProject.name = "fnc314-kmp"