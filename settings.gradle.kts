pluginManagement {
    includeBuild("tools")

    plugins {
        id("com.android.settings") version("8.13.1")
        id("com.android.library") version("8.13.1")
        id("com.android.application") version("8.13.1")
        id("com.android.kotlin.multiplatform.library") version("8.13.1")
        id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("2.0.0")
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
        id("org.jetbrains.compose.hot-reload") version("1.0.0-beta04")
        id("org.jetbrains.compose") version("1.8.2")
        id("org.jetbrains.kotlin.android") version(embeddedKotlinVersion)
        id("org.jetbrains.kotlin.jvm") version(embeddedKotlinVersion)
        id("org.jetbrains.kotlin.multiplatform") version(embeddedKotlinVersion)
        id("org.jetbrains.kotlin.plugin.compose") version(embeddedKotlinVersion)
        id("org.jetbrains.kotlin.plugin.serialization") version(embeddedKotlinVersion)
    }

    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.namespace?.startsWith("org.jetbrains.kotlin") == true -> useVersion(embeddedKotlinVersion)
                requested.id.id.startsWith("com.android") -> useVersion("8.13.1")
                else -> useVersion(requested.version)
            }
        }
    }
}

plugins {
    id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("2.0.0")
    id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
    id("com.android.settings") version("8.13.1")
}

android {
    compileSdk = 36
    minSdk = 24
    buildToolsVersion = "36.1.0"
}

projectCollections {
  "apps" toDepthOf 1
  "components" toDepthOf 1
  "design-system" toDepthOf 1
  "features" toDepthOf 2

  fileSpec.set { file -> file.name.first().toString() !in listOf(".", "-", "_",) }
}

rootProject.name = "fnc314-kmp"
