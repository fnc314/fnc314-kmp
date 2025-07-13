@file:[
    Suppress("UnstableApiUsage")
    OptIn(ExperimentalStdlibApi::class)
]

import org.gradle.kotlin.dsl.support.listFilesOrdered
import java.nio.file.FileSystems

/**
 * Performs iterative checks against receiving [File] ensuring [File.isDirectory] and
 *   that the [File.name] *does not* start with `"_"` or `"."`
 * @receiver A [File] instance
 * @return `true` if [File] is eligible for [Settings.include]
 */
private fun File.isEligibleForGradleInclusion(): Boolean =
    isDirectory and (name.first().toString() !in listOf("_", "."))

/**
 * Assumes this [File] is [File.isDirectory] and invokes [File.listFilesOrdered]
 *   with the use of [File.isEligibleForGradleInclusion] filtering
 * @receiver A [File] instance
 * @returns A [List] of [File]s which are eligible for [Settings.include] invocations
 */
private fun File.expandIntoGradleProjects(): List<File> =
    listFilesOrdered { it.isEligibleForGradleInclusion() }

/**
 * Reduces this [File] to a [List] of [File]s which represent a collection of [File]s
 *   for which [isEligibleForGradleInclusion] is true
 * @receiver A [File] instance
 * @param nesting An [Int] representing the number of iterations of [Iterable.flatMap]
 *   required to fully expand this particular [File].  Default is 1
 * @returns A [List] of [File]s qualifying for [Settings.include] invocations
 * @see isEligibleForGradleInclusion
 */
private fun File.gradleProjectFiles(nesting: Int = 1): List<File> {
    val projFiles: MutableList<File> = mutableListOf()
    var round = 0
    while (round in 0 ..< nesting) {
        if (projFiles.isEmpty()) {
            projFiles.addAll(expandIntoGradleProjects())
        } else {
            val flatMappedFiles = projFiles.flatMap { it.expandIntoGradleProjects() }
            projFiles.clear()
            projFiles.addAll(flatMappedFiles)
        }
        round++
    }
    return projFiles
}

/**
 * Converts this [List] of [File]s to a [List] of [String]s constructed
 *   for [Settings.include] calls
 * @receiver A [List] of [File]s
 * @param topLevelDirName The top-level (i.e. - sibling to this settings file)
 *   directory name
 * @param nesting An [Int] indicating how many layers of [File.getParentFile] are needed
 *   to include in the final [String]
 * @returns A [List] of [String]s for [Settings.include]
 * @see gradleProjectFiles
 */
private fun List<File>.toGradleSettingsIncludeFormats(): List<String> = map {
    it.absolutePath
        .substringAfter(settingsDir.absolutePath)
        .replace(FileSystems.getDefault().separator, ":")
}

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
    val nesting = when (dir) {
        "features" -> 2
        else -> 1
    }
    file(dir)
        .gradleProjectFiles(nesting = nesting)
        .toGradleSettingsIncludeFormats()
        .onEach { include(it) }
}

include(":composeApp")

rootProject.name = "fnc314-kmp"