plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    // Fuerza toolchain JDK 17 (Gradle la descargará si no está instalada)
    jvmToolchain(17)

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Dependencia del módulo domain
                implementation(project(":domain"))
                
                // Ktor para llamadas API
                implementation(libs.bundles.ktor.common)
                implementation(libs.kotlinx.serialization.json)
                
                // SQLDelight para base de datos local
                implementation(libs.bundles.sqldelight.common)
                
                // Coroutines
                implementation(libs.kotlinx.coroutines.core)
                
                // DateTime
                implementation(libs.kotlinx.datetime)
                
                // DateTime
                implementation(libs.kotlinx.datetime)
            }
        }
        
        val androidMain by getting {
            dependencies {
                // Ktor engine para Android
                implementation(libs.ktor.client.okhttp)
                
                // SQLDelight driver para Android
                implementation(libs.sqldelight.android.driver)
            }
        }
        
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                // Ktor engine para iOS
                implementation(libs.ktor.client.darwin)
                
                // SQLDelight driver para iOS
                implementation(libs.sqldelight.native.driver)
            }
        }
        
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "gas.control.project.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("GasControlDatabase") {
            packageName.set("gas.control.project.data.local")
        }
    }
}

