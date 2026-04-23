plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlinJvm)
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        create("kmp-feature-plugin") {
            id = libs.plugins.tools.gradle.plugins.kmp.feature.get().pluginId
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpFeaturePlugin"
            displayName = "fnc314 KMP Feature Plugin"
            description = "Plugin to unify shared configurations across 'Feature' modules"
        }
        create("kmp-design-system-plugin") {
            id = libs.plugins.tools.gradle.plugins.kmp.design.system.get().pluginId
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpDesignSystemPlugin"
            displayName = "fnc314 KMP Design System Plugin"
            description = "Plugin to unify shared configurations across 'Design System' modules"
        }
        create("kmp-component-plugin") {
            id = libs.plugins.tools.gradle.plugins.kmp.component.get().pluginId
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpComponentPlugin"
            displayName = "fnc314 KMP Component Plugin"
            description = "Plugin to unify shared configurations across 'Component' modules"
        }
        create("kmp-compose-app-plugin") {
            id = libs.plugins.tools.gradle.plugins.kmp.compose.app.get().pluginId
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpAppComposePlugin"
            displayName = "fnc314 KMP Compose App Plugin"
            description = "Plugin to unify shared configurations across 'App' modules"
        }
        create("kmp-android-app-plugin") {
          id = libs.plugins.tools.gradle.plugins.kmp.android.app.get().pluginId
          implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpAppAndroidPlugin"
          displayName = "fnc314 KMP Android App Plugin"
          description = "Android App Plugin"
        }
    }
}

dependencies {
    compileOnly(gradleKotlinDsl())
    compileOnly(libs.bundles.kotlin.gradle.plugin)
    compileOnly(libs.bundles.agp)
    implementation(libs.kotlin.stdlib)
    compileOnly(libs.bundles.build.konfig)
    compileOnly(libs.bundles.agp.lint)
}
