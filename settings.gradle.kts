pluginManagement {
    includeBuild("tools")

    val kotlinVersion = "2.3.21"

    plugins {
        id("com.android.settings") version("9.2.0")
        id("com.android.library") version("9.2.0")
        id("com.android.application") version("9.2.0")
        id("com.android.kotlin.multiplatform.library") version("9.2.0")
        id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("3.0.2")
        id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
        id("org.jetbrains.compose.hot-reload") version("1.0.0")
        id("org.jetbrains.compose") version("1.10.3")
        id("org.jetbrains.kotlin.android") version(kotlinVersion)
        id("org.jetbrains.kotlin.jvm") version(kotlinVersion)
        id("org.jetbrains.kotlin.multiplatform") version(kotlinVersion)
        id("org.jetbrains.kotlin.plugin.compose") version(kotlinVersion)
        id("org.jetbrains.kotlin.plugin.serialization") version(kotlinVersion)
    }

    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.namespace?.startsWith("org.jetbrains.kotlin") == true -> useVersion(kotlinVersion)
                requested.id.id.startsWith("com.android") -> useVersion("9.2.0")
                else -> useVersion(requested.version)
            }
        }
    }
}

plugins {
    id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("3.0.2")
    id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
    id("com.android.settings") version("9.2.0")
}

android {
    compileSdk = 37
    targetSdk {
      version = release(37)
    }
    minSdk = 24
    buildToolsVersion = "37.0.0"
}

projectCollections {
  "apps" toDepthOf 1
  "components" toDepthOf 1
  "design-system" toDepthOf 1
  "features" toDepthOf 2

  fileSpec.set { file -> file.name.first().toString() !in listOf(".", "-", "_",) }
}

rootProject.name = "fnc314-kmp"
