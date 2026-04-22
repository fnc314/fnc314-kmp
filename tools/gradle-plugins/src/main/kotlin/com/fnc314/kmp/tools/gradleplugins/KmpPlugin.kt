package com.fnc314.kmp.tools.gradleplugins

import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import java.util.Properties

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
                apiVersion.set(KotlinVersion.KOTLIN_2_3)
                languageVersion.set(KotlinVersion.KOTLIN_2_3)
                optIn.addAll(
                    "kotlin.time.ExperimentalTime",
                    "kotlin.ExperimentalStdlibApi",
                    "kotlin.ExperimentalMultiplatform",
                    "kotlin.ExperimentalUnsignedTypes",
                    "kotlin.experimental.ExperimentalTypeInference",
                    "kotlin.uuid.ExperimentalUuidApi",
                    "kotlin.contracts.ExperimentalContracts",
                )
            }
        }
    }

    /**
     * Configures the [KotlinMultiplatformExtension.iosX64], [KotlinMultiplatformExtension.iosArm64], and
     *   [KotlinMultiplatformExtension.iosSimulatorArm64] targets
     * @receiver A [KotlinMultiplatformExtension] instance
     * @param binaryBaseName The binary base name for all iOS targets
     */
    protected fun KotlinMultiplatformExtension.prepareIOSTargets(
        binaryBaseName: String
    ) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).onEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = binaryBaseName
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

                findLibrary("koin.bom")
                    .ifPresent {
                        project.dependencies.platform(it.get())
                    }
                findBundle("koin")
                    .ifPresent { bundle ->
                        bundle.get().onEach {
                            implementation(it)
                        }
                    }
                findBundle("kotlin.libs").ifPresent { bundle ->
                    bundle.get().onEach {
                        implementation(it)
                    }
                }
                findLibrary("napier")
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
    protected fun Project.kotlinMultiplatformConfiguration(
        config: KotlinMultiplatformExtension.() -> Unit
    ) {
        extensions.findByType(KotlinMultiplatformExtension::class.java)?.let {
            config(it)
        }
    }

    /**
     * Configures the [BuildKonfigExtension] bound to the receiving [Project]
     * @receiver A [Project] instance
     * @param config A callback into which the [BuildKonfigExtension] is a receiver
     */
    protected fun Project.configureBuildKonfig(
        config: BuildKonfigExtension.() -> Unit
    ) {
        extensions.findByType(BuildKonfigExtension::class.java)?.let {
            config(it)
        }
    }

    /**
     * Configures the [com.android.build.api.dsl.CommonExtension] from the Android Gradle Plugins (Library or Application)
     * @receiver A [Project] instance
     * @param config A callback into which the [com.android.build.api.dsl.CommonExtension] is a receiver
     */
    protected fun Project.configureAndroidCommonExtension(
        config: KotlinMultiplatformAndroidComponentsExtension.() -> Unit
    ) {
        extensions.configure<KotlinMultiplatformAndroidComponentsExtension>(config)
    }

    /** Invoked within [apply] before shared configurations */
    open fun Project.beforePluginApplication() { }

    /** Invoked within [apply] to further configure the [Project] */
    open fun Project.afterPluginApplication() { }

    final override fun apply(project: Project) {
        project.beforePluginApplication()

        project.applyKotlinComposeAndroidPlugins(
            kmpPluginTarget = kmpPluginTarget
        )

        project.logger.error(
          """
          | Project ${project.name} Plugin ${project.plugins.joinToString(", ")}
          | KMP PLUGIN TARGET $kmpPluginTarget
          | Extensions ${
            project.extensions.extensionsSchema.joinToString(", ") { it.name }
          }
          | Configurations ${project.configurations.joinToString(", ") { it.name }}
          """.trimMargin("| ")
        )

        if (kmpPluginTarget != KmpPluginTarget.AGGREGATE) {
          project.configureBuildKonfig {
            exposeObjectWithName = "BuildKonfig"
            packageName = kmpPluginTarget.calculateNamespace(project = project)
            defaultConfigs {
              project.rootDir
                .resolve("local.properties")
                .reader(charset = Charsets.UTF_8)
                .use { reader ->
                  Properties().apply { load(reader) }
                }
                .getProperty("kotzilla.analytics.key")
                .let { property ->
                  it.buildConfigField(
                    type = STRING,
                    name = "KOTZILLA_ANALYTICS_KEY",
                    value = property
                  )
                }
            }
          }

          project.kotlinMultiplatformConfiguration {

            if (kmpPluginTarget == KmpPluginTarget.APP) {
              androidTarget {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions {
                  jvmTarget.set(JvmTarget.JVM_17)
                  apiVersion.set(KotlinVersion.KOTLIN_2_3)
                  languageVersion.set(KotlinVersion.KOTLIN_2_3)
                  optIn.addAll(
                    "kotlin.time.ExperimentalTime",
                    "kotlin.ExperimentalStdlibApi",
                    "kotlin.ExperimentalMultiplatform",
                    "kotlin.ExperimentalUnsignedTypes",
                    "kotlin.experimental.ExperimentalTypeInference",
                    "kotlin.uuid.ExperimentalUuidApi",
                    "kotlin.contracts.ExperimentalContracts",
                  )
                }
              }
            }

            compilerOptions {
              apiVersion.set(KotlinVersion.KOTLIN_2_3)
              languageVersion.set(KotlinVersion.KOTLIN_2_3)
              optIn.addAll(
                "kotlin.time.ExperimentalTime",
                "kotlin.ExperimentalStdlibApi",
                "kotlin.ExperimentalMultiplatform",
                "kotlin.ExperimentalUnsignedTypes",
                "kotlin.experimental.ExperimentalTypeInference",
                "kotlin.uuid.ExperimentalUuidApi",
                "kotlin.contracts.ExperimentalContracts",
              )
            }

            prepareIOSTargets(
              binaryBaseName = kmpPluginTarget.calculateIosTargetBinaryBaseName(project = project)
            )

            jvm(name = "desktop")

            prepareCommonMainSourceSets(
              versionCatalog = project.versionCatalog()
            )

            project.versionCatalog().run {
              sourceSets.apply {
                findLibrary("androidx.ui.tooling").ifPresent {
                  androidMain.dependencies {
                    implementation(it)
                  }
                  // project.dependencies.addProvider("debugImplementation", it)
                }
              }
            }
          }

          if (kmpPluginTarget != KmpPluginTarget.APP) {
            project.configureAndroidCommonExtension {
              finalizeDsl { dsl ->
                dsl.namespace = kmpPluginTarget.calculateNamespace(project = project)

                project.versionCatalog().run {
                  findVersion("android.sdk.compile").ifPresent {
                    dsl.compileSdk {
                      version = release(it.requiredVersion.toInt())
                    }
                  }
                }
              }
            }
          }
        }

        project.versionCatalog().run {
            findBundle("kotlin.libs").ifPresent { bundle ->
                bundle.orNull?.onEach {
                    project.dependencies.addProvider(
                      if (kmpPluginTarget == KmpPluginTarget.APP) { "implementation" } else { "commonMainImplementation" },
                      project.provider { it }
                    )
                }
            }
        }

        project.afterPluginApplication()
    }
}