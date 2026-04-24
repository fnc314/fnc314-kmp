plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.feature)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.designSystem.widgets)
    }
  }
}