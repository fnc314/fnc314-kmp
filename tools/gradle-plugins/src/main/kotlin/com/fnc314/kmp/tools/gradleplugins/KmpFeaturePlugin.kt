package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import javax.inject.Inject

internal abstract class KmpFeaturePlugin @Inject constructor(

): Plugin<Project> {
    override fun apply(project: Project) {
        VersionCatalogPlugins.entries.onEach { catalogPlugin ->
            project.versionCatalog().findPlugin(
                catalogPlugin.alias
            ).ifPresent {
                project.pluginManager.apply(it.get().pluginId)
            }
        }

        project.extensions.configure<com.android.build.api.dsl.LibraryExtension> {
            val parentName = project.parent!!.name.replace("_", "")
            namespace = "com.fnc314.kmp.feature.$parentName.${project.name}"
        }
    }
}