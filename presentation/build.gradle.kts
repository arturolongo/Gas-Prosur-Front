plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    // Fuerza toolchain JDK 17 (Gradle la descargará si no está instalada)
    jvmToolchain(17)

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Presentation"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Dependencias de los módulos domain y data
                implementation(project(":domain"))
                implementation(project(":data"))
                
                // Compose Multiplatform
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.compose.material.icons.extended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                
                // Voyager Navigation
                implementation(libs.bundles.voyager)
                
                // Koin para inyección de dependencias
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                
                // Lifecycle
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                
                // Coroutines
                implementation(libs.kotlinx.coroutines.core)
                
                // DateTime
                implementation(libs.kotlinx.datetime)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.ktx)
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "gas.control.project.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

