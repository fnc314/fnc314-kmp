plugins {
  // this is necessary to avoid the plugins to be loaded multiple times
  // in each subproject's classloader
  alias(libs.plugins.androidApplication) apply (false)
  alias(libs.plugins.androidLibrary) apply (false)
  alias(libs.plugins.androidMultiplatformLibrary) apply (false)
  alias(libs.plugins.composeMultiplatform) apply (false)
  alias(libs.plugins.composeHotReload) apply (false)
  alias(libs.plugins.composeCompiler) apply (false)
  alias(libs.plugins.kotlinMultiplatform) apply (false)
  alias(libs.plugins.kotlin.plugin.serialization) apply (false)
  alias(libs.plugins.kotzilla) apply (false)
  alias(libs.plugins.tools.gradle.plugins.kmp.app.android) apply (false)
  alias(libs.plugins.tools.gradle.plugins.kmp.app.compose) apply (false)
  alias(libs.plugins.tools.gradle.plugins.kmp.component) apply (false)
  alias(libs.plugins.tools.gradle.plugins.kmp.design.system) apply (false)
  alias(libs.plugins.tools.gradle.plugins.kmp.feature) apply (false)
  alias(libs.plugins.build.konfig) apply (false)
  alias(libs.plugins.dependency.conflict.analyzer) apply (false)
}