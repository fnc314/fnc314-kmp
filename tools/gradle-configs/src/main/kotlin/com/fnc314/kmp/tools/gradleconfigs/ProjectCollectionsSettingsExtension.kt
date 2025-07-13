package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.provider.MapProperty

/**
 * Attached to a [org.gradle.api.initialization.Settings] object to configure the
 *   [ProjectCollectionsSettingsPlugin]
 */
interface ProjectCollectionsSettingsExtension {
    /** @hide */
    companion object {
        /** The name for the actual extension found in [org.gradle.api.initialization.Settings.getExtensions] */
        const val EXTENSION_NAME: String = "projectCollections"
    }

    /**
     * A [Map] of the top-level directory name (as a [String]) as well as the [Int] representing how
     *   nested projects are within the top-level directory
     * ```kotlin
     * projectCollections.projectCollections.putAll(
     *     mapOf(
     *         "top-level" to 1,
     *         "nested-features" to 3, // means "nested-features/layer-1/layer-2/layer-3"
     *         "components" to 1,
     *         // ...
     *     )
     * )
     * ```
     */
    val projectCollections: MapProperty<String, Int>

    /**
     * Register a collection of 1-level-deep projects
     * ```kotlin
     * projectCollections {
     *     registerProjectCollection("some-dir")
     * }
     * ```
     * @param topLevelDir The collection name/top-level directory
     */
    fun registerProjectCollection(topLevelDir: String)

    /**
     * Register a collection of nested projects
     * ```kotlin
     * projectCollections {
     *     registerNestedProjectCollection("some-dir", nesting = 3)
     *     // implies "some-dir/first/second/desired"
     * }
     * ```
     * @param topLevelDir The collection name/top-level directory
     * @param nesting The depth within [topLevelDir] which must be traversed to find a desired project
     */
    fun registerNestedProjectCollection(topLevelDir: String, nesting: Int)
}