plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.app)
}

android {
  namespace = "com.fnc314.kmp.app.android"
}

dependencies {
  implementation(projects.apps.compose)
}