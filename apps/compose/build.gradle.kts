@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.tools.gradle.plugins.kmp.compose.app)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.navigation)
            implementation(projects.designSystem.widgets)
            implementation(projects.features.posts.list)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        val desktopMain by getting {
            dependencies {
              implementation(libs.kotlinx.coroutinesSwing)
            }
        }
    }

    android {
        namespace = "com.fnc314.kmp.app.compose"
        enableCoreLibraryDesugaring = true
        minSdk = libs.versions.android.sdk.min.map { it.toInt() }.get()

        compileSdk {
          version = release(libs.versions.android.sdk.compile.map { it.toInt() }.get())
        }

        packaging {
          resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
          }
        }

        aarMetadata {
          minCompileSdk = libs.versions.android.sdk.min.map { it.toInt() }.get()
          minAgpVersion = libs.versions.agp.asProvider().get()
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

dependencies {
  coreLibraryDesugaring(libs.android.tools.core.library.desugaring)
}
