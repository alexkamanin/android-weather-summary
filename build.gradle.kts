import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath(libs.android.gradle.core)
        classpath(libs.kotlin.gradle.core)
        classpath(libs.hilt.gradle.core)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
    subprojects {
        afterEvaluate {
            if ((this.plugins.hasPlugin("android") || this.plugins.hasPlugin("android-library"))) {
                this.project.configure<BaseExtension> {
                    setBuildTypes()
                }
            }
        }
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.OptIn"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
    }
}

fun BaseExtension.setBuildTypes() {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BACKEND_ENDPOINT",
                "\"http://api.openweathermap.org/data/2.5/\""
            )
            buildConfigField(
                "String",
                "BACKEND_IMAGE_FORMAT",
                "\"http://openweathermap.org/img/wn/%s@2x.png\""
            )
            buildConfigField("String", "OPEN_WEATHER_API", getLocalProperty("apiKey"))
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BACKEND_ENDPOINT",
                "\"http://api.openweathermap.org/data/2.5/\""
            )
            buildConfigField(
                "String",
                "BACKEND_IMAGE_FORMAT",
                "\"http://openweathermap.org/img/wn/%s@2x.png\""
            )
            buildConfigField("String", "OPEN_WEATHER_API", getLocalProperty("apiKey"))
        }
    }
}

fun getLocalProperty(property: String): String {
    val properties = Properties().apply { load(FileInputStream("local.properties")) }
    return "\"${properties.getProperty(property)}\""
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
//}