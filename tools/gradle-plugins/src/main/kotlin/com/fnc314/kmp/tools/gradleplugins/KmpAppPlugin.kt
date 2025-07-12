package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.Project

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "app" produced by the repository
 */
internal abstract class KmpAppPlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.APP) {
    override fun Project.afterPluginApplication() {
        versionCatalog().run {
            findPlugin("kotzilla")
                .ifPresent {
                    pluginManager.apply(it.get().pluginId)
                }
            kotlinMultiplatformConfiguration {
                sourceSets.commonMain.dependencies {
                    findLibrary("kotzilla.sdk")
                        .ifPresent {
                            implementation(it.get())
                        }
                }
            }
        }
    }
}