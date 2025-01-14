plugins {
    id("kotlin-kapt")

    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.khoahoang183.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}


val versionDagger by extra { "2.48.1" }
val versionHilt by extra { "1.2.0" }

dependencies{
    api(project(":core:data"))

    implementation("com.google.dagger:hilt-android:${versionDagger}")
    implementation("androidx.hilt:hilt-work:${versionHilt}")
    implementation("androidx.browser:browser:1.7.0")
    kapt("com.google.dagger:hilt-android-compiler:${versionDagger}")
    kapt("androidx.hilt:hilt-compiler:${versionHilt}")
}