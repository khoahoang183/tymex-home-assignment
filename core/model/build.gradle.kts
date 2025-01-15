plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.khoahoang183.model"
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
}

val versionRetrofit by extra { "2.9.0" }
val versionRoom by extra { "2.5.1" }

dependencies{
    api("com.squareup.retrofit2:converter-gson:${versionRetrofit}")

    // room
    api("androidx.room:room-runtime:${versionRoom}")
    annotationProcessor("androidx.room:room-compiler:${versionRoom}")
    kapt("androidx.room:room-compiler:${versionRoom}")
    api("androidx.room:room-ktx:${versionRoom}")
}