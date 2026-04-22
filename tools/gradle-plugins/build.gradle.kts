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
        create("kmp-app-plugin") {
            id = libs.plugins.tools.gradle.plugins.kmp.app.get().pluginId
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpAppPlugin"
            displayName = "fnc314 KMP App Plugin"
            description = "Plugin to unify shared configurations across 'App' modules"
        }
    }
}

dependencies {
    compileOnly(gradleKotlinDsl())
    implementation(libs.bundles.kotlin.gradle.plugin)
    implementation(libs.bundles.agp)
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.build.konfig)
}