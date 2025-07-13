package com.fnc314.kmp.tools.gradleconfigs

import javax.inject.Inject

/**
 * Implements [ProjectCollectionsSettingsExtension] to expose [registerProjectCollection] and
 *   [registerNestedProjectCollection] methods
 */
internal abstract class ProjectCollectionsSettingsExtensionImpl @Inject constructor(

) : ProjectCollectionsSettingsExtension {
    override fun registerProjectCollection(topLevelDir: String) {
        projectCollections.put(topLevelDir, 1)
    }

    override fun registerNestedProjectCollection(topLevelDir: String, nesting: Int) {
        projectCollections.put(topLevelDir, nesting)
    }
}