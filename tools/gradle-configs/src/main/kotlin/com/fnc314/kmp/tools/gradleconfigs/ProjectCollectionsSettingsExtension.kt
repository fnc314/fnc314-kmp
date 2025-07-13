package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.provider.MapProperty

/**
 * Attached to a [org.gradle.api.initialization.Settings] object to configure the
 *   [ProjectCollectionsSettingsPlugin]
 */
interface ProjectCollectionsSettingsExtension {
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
}