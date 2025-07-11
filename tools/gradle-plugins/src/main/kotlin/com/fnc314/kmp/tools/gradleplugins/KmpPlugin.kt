package com.fnc314.kmp.tools.gradleplugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

internal sealed class KmpPlugin(
    protected val kmpPluginTarget: KmpPluginTarget,
): Plugin<Project> {
    /**
     * Applies necessary configurations to [KotlinMultiplatformExtension.androidTarget]
     * @receiver A [KotlinMultiplatformExtension] instance
     */
    protected fun KotlinMultiplatformExtension.prepareAndroidTarget() {
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
                apiVersion.set(KotlinVersion.KOTLIN_2_2)
                languageVersion.set(KotlinVersion.KOTLIN_2_2)
                optIn.addAll(
                    "kotlin.time.ExperimentalTime",
                )
            }
        }
    }

    /**
     * Configures the [KotlinMultiplatformExtension.iosX64], [KotlinMultiplatformExtension.iosArm64], and
     *   [KotlinMultiplatformExtension.iosSimulatorArm64] targets
     * @receiver A [KotlinMultiplatformExtension] instance
     * @param parentProjectName The [String] for the [Project.getParent]
     * @param projectName The [String] for [Project.name]
     */
    protected fun KotlinMultiplatformExtension.iOSTargetPrep(
        project: Project
    ) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).onEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = kmpPluginTarget.calculateIosTargetBinaryBaseName(project = project)
                isStatic = true
            }
        }
    }

    /**
     * Prepares [KotlinMultiplatformExtension.sourceSets.commonMain] with the provided [versionCatalog]
     * @receiver A [KotlinMultiplatformExtension] instance
     * @param versionCatalog A [VersionCatalog]
     */
    protected fun KotlinMultiplatformExtension.prepareCommonMainSourceSets(
        versionCatalog: VersionCatalog
    ) {
        versionCatalog.run {
            sourceSets.commonMain.dependencies {
                findBundle("compose")
                    .ifPresent { bundle ->
                        bundle.get().onEach {
                            implementation(it)
                        }
                    }
                findLibrary("kotlinx.serialization.json")
                    .ifPresent {
                        implementation(it.get())
                    }
                findLibrary("kotlin.stdlib")
                    .ifPresent {
                        implementation(it.get())
                    }
            }
        }
    }

    /**
     * Exposes a [config] callback on the [KotlinMultiplatformExtension] found on the
     *   target [Project]
     * @receiver A [Project] instance
     * @param config A callback with [KotlinMultiplatformExtension] as the receiver
     */
    protected fun Project.configureKotlin(
        config: KotlinMultiplatformExtension.() -> Unit
    ) {
        extensions.configure<KotlinMultiplatformExtension>(config)
    }

    /** Invoked within [apply] to further configure the [Project] */
    open fun Project.afterPluginApplication() { }

    final override fun apply(project: Project) {
        project.applyKotlinComposeAndroidPlugins(
            kmpPluginTarget = kmpPluginTarget
        )

        project.configureKotlin {
            prepareAndroidTarget()

            iOSTargetPrep(
                project = project
            )

            jvm(name = "desktop")

            prepareCommonMainSourceSets(
                versionCatalog = project.versionCatalog()
            )

            project.versionCatalog().run {
                sourceSets.apply {

                    findLibrary("androidx.ui.tooling").ifPresent {
                        androidMain.dependencies {
                            implementation(it.get())
                        }
                        project.dependencies.addProvider("debugImplementation", it)
                    }
                }
            }
        }

        project.extensions.configure<com.android.build.gradle.BaseExtension> {
            namespace = kmpPluginTarget.calculateNamespace(project = project)

            project.versionCatalog().run {
                findVersion("android.sdk.compile").ifPresent {
                    compileSdkVersion = "android-${it.requiredVersion}"
                }
                findVersion("android.sdk.min").ifPresent {
                    defaultConfig.minSdk = it.requiredVersion.toInt()
                }
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        project.versionCatalog().findLibrary("kotlin.stdlib").ifPresent {
            project.dependencies.addProvider("implementation", it)
        }

        project.afterPluginApplication()
    }
}