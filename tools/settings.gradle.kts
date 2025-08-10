pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version(embeddedKotlinVersion)
        id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("1.0.6")
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

plugins {
    id("com.fnc314.gradle.plugins.settings.project-collections-gradle-settings-plugin") version("1.0.6")
}

// include(":gradle-configs")
include(":gradle-plugins")
rootProject.name = "fnc314-kmp-tools"