import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.tools.gradle.plugins.kmp.feature)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            apiVersion.set(KotlinVersion.KOTLIN_2_2)
            languageVersion.set(KotlinVersion.KOTLIN_2_2)
            optIn.addAll(
                "ExperimentalTime",
                "UnstableApi",
            )
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DesignSystemWidgets"
            isStatic = true
        }
    }

    jvm(name = "desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.uiUtil)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            // implementation(libs.bundles.androidx.compose.material3)
            // implementation(libs.bundles.androidx.compose.material3.adaptive)
        }
        androidMain.dependencies {
            implementation(libs.androidx.ui.tooling)
        }
    }
}

android {
    namespace = "com.fnc314.kmp.features.posts.list"
    compileSdkVersion(libs.versions.android.compileSdk.get().toInt())

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}