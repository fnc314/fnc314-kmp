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
            id = "com.fnc314.kmp.tools.gradle.plugin.feature"
            implementationClass = "com.fnc314.kmp.tools.gradleplugins.KmpFeaturePlugin"
            displayName = "FNC314 KMP Feature Plugin"
            description = "Plugin to unify shared configurations across 'Feature' modules"
        }
    }
}

dependencies {
    compileOnly(gradleKotlinDsl())
    implementation(libs.agp.api)
    implementation(libs.kotlin.stdlib)
}