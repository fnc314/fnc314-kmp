package com.fnc314.kmp.tools.gradleplugins

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "app" produced by the repository
 */
internal abstract class KmpAppPlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.APP) {
    override fun Project.afterPluginApplication() {
        extensions.configure<ApplicationAndroidComponentsExtension> {
          finalizeDsl { dsl ->
            dsl.namespace = ""
          }
        }
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