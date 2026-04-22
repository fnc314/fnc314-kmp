import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.tools.gradle.plugins.kmp.app)
}

kotlin {
    sourceSets {

        val commonMain by creating {
          dependencies {
            implementation(projects.components.navigation)
            implementation(projects.designSystem.widgets)
            implementation(projects.features.posts.list)
          }
        }

        val commonTest by creating {
          dependencies {
            implementation(libs.kotlin.test)
          }
        }

        val desktopMain by creating {
            dependencies {
              // implementation(compose.desktop.currentOs)
              implementation(libs.kotlinx.coroutinesSwing)
            }
        }
    }
}

androidComponents {
  finalizeDsl { dsl ->
    dsl.namespace = "com.fnc314.kmp.app.compose"

    dsl.defaultConfig {
      applicationId = "com.fnc314.kmp.app.compose.android"
      versionCode = 1
      versionName = "1.0"
    }

    dsl.packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
    }

    dsl.buildTypes {
      getByName("release") {
        isMinifyEnabled = false
      }
    }

    dsl.dependenciesInfo {
      includeInApk = false
      includeInBundle = false
    }
  }
}

compose.desktop {
    application {
        mainClass = "com.fnc314.kmp.app.compose.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.fnc314.kmp.app.compose"
            packageVersion = "1.0.0"
        }
    }
}