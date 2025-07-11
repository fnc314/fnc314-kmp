package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.internal.serialize.codecs.core.NodeOwner

/**
 * Finds the [VersionCatalog] with the provided [name], defaulting to `"libs"`
 * @receiver A [Project] instance
 * @param name A [String] to pass into [VersionCatalogsExtension.named], defaulting to `"libs"`
 * @return The [VersionCatalog] by the name of [name]
 */
internal fun Project.versionCatalog(name: String = "libs"): VersionCatalog =
    extensions
        .getByType(VersionCatalogsExtension::class.java)
        .named(name)

internal enum class VersionCatalogPlugins(
    val alias: String
) {
    ANDROID_LIBRARY("androidLibrary"),
    // ANDROID_LIBRARY_MULTIPLATFORM("androidMultiplatformLibrary"),
    COMPOSE_COMPILER("composeCompiler"),
    COMPOSE_MULTIPLATFORM("composeMultiplatform"),
    // COMPOSE_HOT_RELOAD("composeHotReload"),
    KOTLIN_MULTIPLATFORM("kotlinMultiplatform"),
    KOTLIN_PLUGIN_SERIALIZATION("kotlin.plugin.serialization"),
    ;
}