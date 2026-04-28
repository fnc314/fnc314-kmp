package com.fnc314.kmp.tools.gradleconfigs

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class GradleConfigsSettingsPlugin : Plugin<Settings> {
  private class GradleConfigsExtensionImpl(
    override val KotlinVersion: String = GradleConfigs.Versions.Kotlin.VERSION,
    override val AndroidSdkTarget: Int = GradleConfigs.Versions.Android.Sdk.TARGET,
    override val AndroidSdkMin: Int = GradleConfigs.Versions.Android.Sdk.MIN,
    override val AndroidSdkCompile: Int = GradleConfigs.Versions.Android.Sdk.COMPILE,
    override val AndroidBuildTools: String = GradleConfigs.Versions.Android.BuildTools.VERSION,
  ): GradleConfigsExtension

  override fun apply(target: Settings) {
    target.extensions.add(
      GradleConfigsExtension::class.java,
      "gradleConfigs",
      GradleConfigsExtensionImpl()
    )
  }
}