plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    compileOnly(gradleKotlinDsl())
}