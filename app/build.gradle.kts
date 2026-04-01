plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.memoflow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.memoflow"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val groqApiKey = providers.gradleProperty("GROQ_API_KEY").orNull ?: ""
        buildConfigField("String", "GROQ_API_KEY", "\"$groqApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.material3)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.gms.play.services.mlkit.text.recognition.korean)
    implementation(libs.gms.play.services.mlkit.text.recognition.japanese)
    implementation(libs.gms.play.services.mlkit.text.recognition.chinese)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.google.hilt.android)

    ksp(libs.google.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.coil.compose)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.gson)
    implementation(libs.squareup.okhttp)

    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.play.services.mlkit.text.recognition.korean)
    implementation(libs.play.services.mlkit.text.recognition.japanese)
    implementation(libs.play.services.mlkit.text.recognition.chinese)

    debugImplementation(libs.androidx.compose.ui.tooling)
}
