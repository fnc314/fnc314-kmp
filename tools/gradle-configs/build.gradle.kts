plugins {
  `java-gradle-plugin`
  alias(libs.plugins.kotlinJvm)
  `kotlin-dsl-precompiled-script-plugins`
  alias(libs.plugins.dependency.conflict.analyzer)
}

dependencyConflictAnalyzer {
  failOnConflict = true
  reportFile = rootProject
    .layout
    .projectDirectory
    .file(
      "../logs/dependency-conflict-analyzer/${rootProject.name}/${path.replace(":", "/")}/dependency-conflict-analyzer.md"
    )
}

kotlin {
  jvmToolchain(libs.versions.jdk.map { it.toInt() }.get())
}

gradlePlugin {
  plugins {
    create("gradle-configs-settings") {
      id = libs.plugins.tools.gradle.configs.gradle.configs.settings.get().pluginId
      implementationClass = "com.fnc314.kmp.tools.gradleconfigs.GradleConfigsSettingsPlugin"
      description = "Predefined values to configure settings.gradle(.kts) files"
      displayName = "fnc314 Gradle Configs Settings Plugin"
    }
  }
}

dependencies {
  compileOnly(gradleKotlinDsl())
}