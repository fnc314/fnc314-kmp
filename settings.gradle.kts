@file:Suppress("UnstableApiUsage")

import org.gradle.kotlin.dsl.support.listFilesOrdered

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
            when (requested.module?.group) {
                "org.jetbrains.kotlin" -> useVersion("2.2.0")
                else -> return@eachPlugin
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("com.android.settings") version "8.11.1"
}

android {
    compileSdk = 36
    targetSdk = 35
    minSdk = 24
    buildToolsVersion = "36.0.0"
}

listOf(
    "components",
    "design-system",
    "features",
).onEach { dir ->
    when (dir) {
        "features" -> file(dir)
            .listFilesOrdered { it.isDirectory }
            .onEach { featureDir ->
                file(featureDir)
                    .listFilesOrdered { it.isDirectory }
                    .onEach { featureSubDir ->
                        include(":$dir:${featureDir.name}:${featureSubDir.name}")
                    }
            }

        else -> file(dir)
            .listFilesOrdered { file ->
                file.isDirectory and (file.name.first().toString() !in listOf("_", "."))
            }
            .onEach { subProj ->
                include(":$dir:${subProj.name}")
            }
    }
}

include(":composeApp")

rootProject.name = "fnc314-kmp"