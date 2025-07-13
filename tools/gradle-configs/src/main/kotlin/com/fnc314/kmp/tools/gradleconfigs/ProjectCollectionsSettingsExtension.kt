package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.provider.MapProperty

/**
 * Attached to a [org.gradle.api.initialization.Settings] object to configure the
 *   [ProjectCollectionsSettingsPlugin]
 */
abstract class ProjectCollectionsSettingsExtension {
    /** @hide */
    companion object {
        /** The name for the actual extension found in [org.gradle.api.initialization.Settings.getExtensions] */
        const val EXTENSION_NAME: String = "projectCollections"
    }

    /**
     * A [Map] of the top-level directory name (as a [String]) as well as the [Int] representing how
     *   nested projects are within the top-level directory.  Unexposed to consumers
     */
    internal abstract val projectCollections: MapProperty<String, Int>

    /**
     * Register a collection of 1-level-deep projects
     * ```kotlin
     * projectCollections {
     *     registerProjectCollection(topLevelDir = "some-dir")
     *     // implies "some-dir/first", "some-dir/second", etc...
     * }
     * ```
     * @param topLevelDir The collection name/top-level directory
     */
    abstract fun registerProjectCollection(topLevelDir: String)

    /**
     * Register a collection of nested projects
     * ```kotlin
     * projectCollections {
     *     registerProjectCollection(topLevelDir = "some-dir", depth = 3)
     *     // implies "some-dir/first/second/desired", "some-dir/other/layer/target", etc...
     * }
     * ```
     * @param topLevelDir The collection name/top-level directory
     * @param depth The depth within [topLevelDir] which must be traversed to find a desired project
     * @see registerProjectCollection
     */
    abstract fun registerProjectCollection(topLevelDir: String, depth: Int)

    /**
     * A friendly-syntax approach to including collections of projects
     * ```kotlin
     * projectCollections {
     *     "apps" withDepthOf 1
     *     "components" withDepthOf 1
     *     "design-system" withDepthOf 1
     *     "features" withDepthOf 2
     *     // means "features/first/project-a", "features/first/project-b", "features/second/project-a", etc...
     * }
     * ```
     * @receiver A [String] interpreted as the top-level directory name
     * @param depth An [Int] indicating how deep into the top-level directory members we are required to traverse
     */
    abstract infix fun String.withDepthOf(depth: Int)

    /**
     * Register a collection of nested projects
     * ```kotlin
     * projectCollections {
     *     registerNestedProjectCollection(topLevelDir = "some-dir", depth = 3)
     *     // implies "some-dir/first/second/desired", "some-dir/other/layer/target", etc...
     * }
     * ```
     * @param topLevelDir The collection name/top-level directory
     * @param depth The depth within [topLevelDir] which must be traversed to find a desired project
     */
    abstract fun registerNestedProjectCollection(topLevelDir: String, depth: Int)
}