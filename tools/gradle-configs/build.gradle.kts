plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlinJvm)
}

gradlePlugin {
    plugins {
        create("kmp-project-collections-settings-plugin") {
            id = "com.fnc314.kmp.tools.gradle.configs.project-collections-settings"
            implementationClass = "com.fnc314.kmp.tools.gradleconfigs.ProjectCollectionsSettingsPlugin"
            description = "Streamlines the calls to `Settings.include` for nested project collections"
            displayName = "KMP fnc314 Gradle Configs Project Collections Settings Plugin"
        }
    }
}

dependencies {
    compileOnly(gradleKotlinDsl())
}