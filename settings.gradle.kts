@file:Suppress("UnstableApiUsage")

import org.gradle.kotlin.dsl.support.listFilesOrdered

/**
 * Performs iterative checks against receiving [File] ensuring [File.isDirectory] and
 *   that the [File.name] *does not* start with `"_"` or `"."`
 * @receiver A [File] instance
 * @return `true` if [File] is eligible for [Settings.include]
 */
private fun File.isEligibleForGradleInclusion(): Boolean =
    isDirectory and (name.first().toString() !in listOf("_", "."))

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
            .listFilesOrdered { it.isEligibleForGradleInclusion() }
            .onEach { featureDir ->
                file(featureDir)
                    .listFilesOrdered { it.isEligibleForGradleInclusion() }
                    .onEach { featureSubDir ->
                        include(":$dir:${featureDir.name}:${featureSubDir.name}")
                    }
            }

        else -> file(dir)
            .listFilesOrdered { it.isEligibleForGradleInclusion() }
            .onEach { subProj ->
                include(":$dir:${subProj.name}")
            }
    }
}

include(":composeApp")

rootProject.name = "fnc314-kmp"