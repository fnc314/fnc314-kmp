plugins {
  alias(libs.plugins.tools.gradle.plugins.kmp.app.android)
}

androidComponents {
  finalizeDsl { dsl ->
    dsl.namespace = "com.fnc314.kmp.app.android"
    dsl.enableKotlin = true
    dsl.buildFeatures {
      compose = true
      resValues = true
    }
    dsl.dependenciesInfo {
      includeInApk = false
      includeInBundle = false
    }
    dsl.composeOptions {
      kotlinCompilerExtensionVersion = null
    }
    dsl.compileSdk {
      version = release(libs.versions.android.sdk.compile.map { it.toInt() }.get())
    }
    dsl.defaultConfig {
      applicationId = "com.fnc314.kmp.app.android"
      versionCode = 1
      versionName = "1.0"
    }
    dsl.compileOptions {
      isCoreLibraryDesugaringEnabled = true
      sourceCompatibility(JavaVersion.VERSION_17)
      targetCompatibility(JavaVersion.VERSION_17)
    }
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
