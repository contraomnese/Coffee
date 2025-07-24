import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
}

val YANDEX_MAPKIT_KEY: String = gradleLocalProperties(rootDir, providers).getProperty("YANDEX_MAPKIT_KEY")
val COFFEE_API_BASE_URL: String = gradleLocalProperties(rootDir, providers).getProperty("COFFEE_API_BASE_URL")

android {
    namespace = "com.contraomnese.coffee"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.contraomnese.coffee"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        android.buildFeatures.buildConfig = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            buildConfigField("String", "YANDEX_MAPKIT_KEY", "\"${YANDEX_MAPKIT_KEY}\"")
            buildConfigField("String", "COFFEE_API_BASE_URL", "\"${COFFEE_API_BASE_URL}\"")
        }
        release {
            buildConfigField("String", "YANDEX_MAPKIT_KEY", "\"${YANDEX_MAPKIT_KEY}\"")
            buildConfigField("String", "COFFEE_API_BASE_URL", "\"${COFFEE_API_BASE_URL}\"")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // modules
    implementation(project(":core:ui"))
    implementation(project(":core:design"))
    implementation(project(":core:presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(project(":features:register"))
    implementation(project(":features:login"))
    implementation(project(":features:locations"))
    implementation(project(":features:menu"))
    implementation(project(":features:order"))
    implementation(project(":features:map"))

    implementation(libs.bundles.koin)
    implementation(libs.bundles.core)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.presentation)
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.yandex.maps.mobile)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.network)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    implementation(libs.bundles.datastore)
    implementation(libs.androidx.security.crypto)

    implementation(libs.androidsvg.aar)
    implementation(libs.accompanist.permissions)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.androidTest)
    debugImplementation(libs.bundles.composeDebug)
}