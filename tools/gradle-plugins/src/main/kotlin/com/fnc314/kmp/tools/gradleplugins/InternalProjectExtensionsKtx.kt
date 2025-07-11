package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

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

/**
 * Applies the set of [VersionCatalogPlugins] from the [VersionCatalog] to the
 *   receiving [Project]
 * @receiver A [Project] instance
 */
internal fun Project.applyKotlinComposeAndroidPlugins(
    kmpPluginTarget: KmpPluginTarget
) {
    versionCatalog().run {
        VersionCatalogPlugins.entries
            .filter {
                when (it) {
                    VersionCatalogPlugins.ANDROID_APPLICATION ->
                        kmpPluginTarget == KmpPluginTarget.APP

                    VersionCatalogPlugins.ANDROID_LIBRARY ->
                        kmpPluginTarget != KmpPluginTarget.APP

                    else -> true
                }
            }
            .onEach { catalogPlugin ->
                findPlugin(catalogPlugin.alias).ifPresent {
                    pluginManager.apply(it.get().pluginId)
                }
            }
    }
}

/**
 * The [VersionCatalog.findPlugin] [alias]s queried from the [VersionCatalog]
 * @property alias The [String] fed into [VersionCatalog.findPlugin] to configure a [Project]
 */
internal enum class VersionCatalogPlugins(
    val alias: String
) {
    ANDROID_APPLICATION("androidApplication"),
    ANDROID_LIBRARY("androidLibrary"),
    // ANDROID_LIBRARY_MULTIPLATFORM("androidMultiplatformLibrary"),
    COMPOSE_COMPILER("composeCompiler"),
    COMPOSE_MULTIPLATFORM("composeMultiplatform"),
    // COMPOSE_HOT_RELOAD("composeHotReload"),
    KOTLIN_MULTIPLATFORM("kotlinMultiplatform"),
    KOTLIN_PLUGIN_SERIALIZATION("kotlin.plugin.serialization"),
    ;
}

/**
 * Implementations of [KmpPlugin] vary by a [KmpPluginTarget] and uses [KmpPluginTarget.namespaceBase]
 */
internal enum class KmpPluginTarget {
    APP,
    COMPONENT,
    DESIGN_SYSTEM,
    FEATURE,
    ;

    /** Used for [com.android.build.api.dsl.CommonExtension.namespace] */
    private val namespaceBase: String
        get() = "com.fnc314.kmp.${name.lowercase().replace("_", "")}"

    /**
     * Calculates the [String] used for [com.android.build.api.dsl.CommonExtension.namespace] leveraging
     *   [Project.getParent] for [FEATURE] cases
     * @param project A [Project] to which a [KmpPlugin] is applied
     */
    fun calculateNamespace(project: Project): String = when (this) {
        FEATURE -> "$namespaceBase.${project.parent!!.name}.${project.name}"
        else -> "$namespaceBase.${project.name}"
    }

    /** Used for [org.jetbrains.kotlin.gradle.plugin.mpp.NativeBinary.baseName] */
    private val iosTargetBinaryBaseNamePrefix: String
        get() = name.split("_")
            .joinToString {
                it.lowercase().replaceFirstChar { char -> char.uppercase() }
            }

    /**
     * Calculates the [org.jetbrains.kotlin.gradle.plugin.mpp.NativeBinary.baseName] conditionally for [FEATURE] cases
     * @param project A [Project] to which a [KmpPlugin] is applied
     */
    fun calculateIosTargetBinaryBaseName(project: Project): String = when (this) {
        FEATURE -> "$iosTargetBinaryBaseNamePrefix${project.name.replaceFirstChar { it.uppercase() }}"
        else -> "$iosTargetBinaryBaseNamePrefix${project.parent!!.name.replaceFirstChar { it.uppercase() }}${project.name.replaceFirstChar { it.uppercase() }}"
    }
}