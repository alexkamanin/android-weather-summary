plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "ru.shurick.enterprise.summary"
        minSdk = 22
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.version.get()
    }
    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splash)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.compose.ui.core)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.activity)
    implementation(libs.compose.icons)

    implementation(libs.landscapist.glide)

    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.controller)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.hilt.navigation)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.work)
    kapt(libs.hilt.work.compiler)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    debugImplementation(libs.compose.ui.tooling)

    implementation(projects.shared.weather)
    implementation(projects.shared.city)
    implementation(projects.libraries.network)
    implementation(projects.libraries.storage)
    implementation(projects.libraries.utils)
}