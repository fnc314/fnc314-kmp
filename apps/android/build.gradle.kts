plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.app)
}

android {
  namespace = "com.fnc314.kmp.app.android"

  defaultConfig {
    applicationId = "com.fnc314.kmp.app.compose.android"
    versionCode = 1
    versionName = "1.0"
  }
}

dependencies {
  implementation(projects.apps.compose)
  implementation(projects.features.posts.list)

  implementation(libs.napier)
  implementation(libs.bundles.koin)
  implementation(libs.bundles.compose)
  implementation(libs.androidx.activity.compose)
}
