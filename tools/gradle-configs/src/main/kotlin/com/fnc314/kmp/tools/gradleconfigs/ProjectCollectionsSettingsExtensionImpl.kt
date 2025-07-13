package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import javax.inject.Inject

/**
 * Implements [ProjectCollectionsSettingsExtension] to expose [registerProjectCollection] and
 *   [registerNestedProjectCollection] methods
 * @param objectFactory An instance of the [org.gradle.api.model.ObjectFactory]
 */
internal abstract class ProjectCollectionsSettingsExtensionImpl @Inject constructor(
    private val objectFactory: ObjectFactory,
) : ProjectCollectionsSettingsExtension() {
    override val projectCollections: MapProperty<String, Int> = objectFactory.mapProperty(
        String::class.java, Int::class.java
    )

    override fun registerProjectCollection(topLevelDir: String) {
        projectCollections.put(topLevelDir, 1)
    }

    override fun registerProjectCollection(topLevelDir: String, depth: Int) {
        projectCollections.put(topLevelDir, depth)
    }

    override infix fun String.withDepthOf(depth: Int) {
        projectCollections.put(this, depth)
    }

    override fun registerNestedProjectCollection(topLevelDir: String, depth: Int) {
        projectCollections.put(topLevelDir, depth)
    }
}