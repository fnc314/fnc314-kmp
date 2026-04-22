plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.app)
}

dependencies {
  implementation(projects.apps.compose)
}