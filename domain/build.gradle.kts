plugins {
    id("java-library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // KotlinDependencies
    implementation(libs.kotlin)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.javax.inject)
    implementation(libs.hilt.core)
}