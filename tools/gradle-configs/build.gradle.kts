plugins {
  `java-gradle-plugin`
  alias(libs.plugins.kotlinJvm)
  `kotlin-dsl-precompiled-script-plugins`
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