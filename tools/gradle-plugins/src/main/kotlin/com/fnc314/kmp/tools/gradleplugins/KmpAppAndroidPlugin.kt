package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "app" produced by the repository
 */
internal class KmpAppAndroidPlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.APP_ANDROID) {
    override fun Project.afterPluginApplication() {
        versionCatalog().run {
            findPlugin("kotzilla")
                .ifPresent {
                    pluginManager.apply(it.get().pluginId)
                }
            dependencies {
              findLibrary("kotzilla.sdk")
                .ifPresent {
                  addProvider("implementation", it)
                }
            }
        }
    }
}