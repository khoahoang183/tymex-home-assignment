plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.khoahoang183.data"
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


val versionRetrofit by extra { "2.9.0" }
val versionMoshi by extra { "1.14.0" }
val versionDagger by extra { "2.48.1" }
val versionOkhttp by extra { "4.10.0" }
val versionHilt by extra { "1.2.0" }
val versionRoom by extra { "2.5.1" }

dependencies {
    api(project(":core:model"))

    // additional library
    api("com.squareup.retrofit2:retrofit:${versionRetrofit}")
    api("com.squareup.retrofit2:converter-gson:${versionRetrofit}")
    api("com.squareup.retrofit2:converter-moshi:${versionRetrofit}")
    api("com.squareup.okhttp3:okhttp:${versionOkhttp}")
    api("com.squareup.okhttp3:logging-interceptor:${versionOkhttp}")

    api("com.squareup.moshi:moshi:${versionMoshi}")
    api("com.squareup.moshi:moshi-adapters:${versionMoshi}")
    api("com.squareup.moshi:moshi-kotlin:${versionMoshi}")

    implementation("com.google.dagger:hilt-android:${versionDagger}")
    kapt("com.google.dagger:hilt-android-compiler:${versionDagger}")
    kapt("androidx.hilt:hilt-compiler:${versionHilt}")

    implementation("com.jakewharton.timber:timber:5.0.1")

    // room
    //implementation("androidx.room:room-runtime:${versionRoom}")
    //annotationProcessor ("androidx.room:room-compiler:${versionRoom}")
    kapt("androidx.room:room-compiler:${versionRoom}")
    //implementation("androidx.room:room-ktx:${versionRoom}")
}