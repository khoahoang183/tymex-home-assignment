plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-parcelize")
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

dependencies{
    api("com.squareup.retrofit2:converter-gson:${versionRetrofit}")
}