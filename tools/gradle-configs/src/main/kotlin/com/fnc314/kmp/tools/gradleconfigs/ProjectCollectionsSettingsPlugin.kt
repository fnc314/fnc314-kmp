package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.listFilesOrdered
import java.io.File
import java.nio.file.FileSystems

/**
 * A [Plugin] for [Settings] objects which streamlines the collection of projects included in
 *   strategically named directories
 */
internal abstract class ProjectCollectionsSettingsPlugin : Plugin<Settings> {

    /**
     * Performs iterative checks against receiving [File] ensuring [File.isDirectory] and
     *   that the [File.name] *does not* start with `"_"` or `"."`
     * @receiver A [File] instance
     * @return `true` if [File] is eligible for [Settings.include]
     */
    private fun File.isEligibleForGradleInclusion(): Boolean =
        isDirectory and (name.first().toString() !in listOf("_", ".", "-",))


    /**
     * Assumes this [File] is [File.isDirectory] and invokes [File.listFilesOrdered]
     *   with the use of [File.isEligibleForGradleInclusion] filtering
     * @receiver A [File] instance
     * @returns A [List] of [File]s which are eligible for [Settings.include] invocations
     * @see isEligibleForGradleInclusion
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
     * @see expandIntoGradleProjects
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
     * @param settingsDir The [File] of this [Settings] object
     * @returns A [List] of [String]s for [Settings.include]
     */
    private fun List<File>.toGradleSettingsIncludeFormats(
        settingsDir: File,
    ): List<String> = map {
        it.absolutePath
            .substringAfter(settingsDir.absolutePath)
            .replace(FileSystems.getDefault().separator, ":")
    }

    override fun apply(target: Settings) {
        target.extensions.create(
            ProjectCollectionsSettingsExtension::class.java,
            ProjectCollectionsSettingsExtension.EXTENSION_NAME,
            ProjectCollectionsSettingsExtensionImpl::class.java
        )
        target.gradle.settingsEvaluated { settings ->
            settings.extensions.getByType<ProjectCollectionsSettingsExtension>()
                .projectCollections
                .get()
                .onEach { (dir, depth) ->
                    settings.settingsDir
                        .resolve(dir)
                        .gradleProjectFiles(nesting = depth)
                        .toGradleSettingsIncludeFormats(settingsDir = settings.settingsDir)
                        .onEach { settings.include(it) }
                }
        }
    }
}