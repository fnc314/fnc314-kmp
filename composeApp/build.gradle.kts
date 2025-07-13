import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.tools.gradle.plugins.kmp.app)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.components.navigation)
            implementation(projects.designSystem.widgets)
            implementation(projects.features.posts.list)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.fnc314.kmp"

    defaultConfig {
        applicationId = "com.fnc314.kmp"
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

compose.desktop {
    application {
        mainClass = "com.fnc314.kmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.fnc314.kmp"
            packageVersion = "1.0.0"
        }
    }
}