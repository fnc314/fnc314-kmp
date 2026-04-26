import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.app.android)
}

kotlin {
  jvmToolchain(
    libs.versions.jdk.map { it.toInt() }.get()
  )
  target {
    compilations.configureEach {
      compileTaskProvider.configure {
        compilerOptions {
          jvmTarget.set(libs.versions.jdk.map { JvmTarget.fromTarget(it) })
          languageVersion.set(
            libs.versions.kotlin.map { KotlinVersion.fromVersion(it.substringBeforeLast(".")) }
          )
          apiVersion.set(
            libs.versions.kotlin.map { KotlinVersion.fromVersion(it.substringBeforeLast(".")) }
          )
        }
      }
    }
  }
}

android {
  namespace = "com.fnc314.kmp.app.android"
  enableKotlin = true
  buildFeatures {
    compose = true
    resValues = true
  }
  dependenciesInfo {
    includeInApk = false
    includeInBundle = false
  }
  composeOptions {
    kotlinCompilerExtensionVersion = null
  }
  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility(JavaVersion.VERSION_17)
    targetCompatibility(JavaVersion.VERSION_17)
  }
  compileSdk {
    version = release(libs.versions.android.sdk.compile.map { it.toInt() }.get())
  }
  defaultConfig {
    applicationId = "com.fnc314.kmp.app.android"
    versionCode = 1
    versionName = "1.0"
  }
}

dependencies {
  coreLibraryDesugaring(libs.android.tools.core.library.desugaring)

  implementation(projects.apps.compose)
  implementation(projects.features.posts.list)

  implementation(libs.napier)
  implementation(libs.bundles.koin)
  implementation(libs.bundles.compose)
  implementation(libs.androidx.activity.compose)
}
