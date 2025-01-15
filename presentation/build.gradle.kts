plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        getByName("main").java.srcDirs("build/generated/source/navigation-args")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val productFlavorDimension by extra { "environment" }
    flavorDimensions.add(productFlavorDimension)
    namespace = "com.khoahoang183.basesource"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khoahoang183.basesource"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("dev") {
            dimension = productFlavorDimension
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            versionCode = 1
            versionName = "1.0.1"

            resValue("string", "app_name", "\"Tymex Dev\"")
        }
        create("prod") {
            dimension = productFlavorDimension
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"

            versionCode = 1
            versionName = "1.0.1"

            resValue("string", "app_name", "\"Tymex Prod\"")
        }
    }

}

val versionMaterial by extra { "1.11.0" }
val versionRetrofit by extra { "2.9.0" }
val versionMoshi by extra { "1.14.0" }
val versionDagger by extra { "2.48.1" }
val versionHilt by extra { "1.2.0" }
val versionMultidex by extra { "2.0.1" }
val versionNavigation by extra { "2.7.7" }
val versionLottie by extra { "3.6.1" }
val versionOkhttp by extra { "4.10.0" }
val versionCoil = "2.6.0"
val versionJUnit = "4.13.2"
val versionMockito = "5.5.0"
val versionMockitoKotlin = "4.1.0"

dependencies {
    api(project(":core:domain"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:${versionMaterial}")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.navigation:navigation-runtime-ktx:${versionNavigation}")
    implementation("androidx.navigation:navigation-fragment-ktx:$versionNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$versionNavigation")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

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
    implementation("androidx.hilt:hilt-work:${versionHilt}")
    implementation("androidx.browser:browser:1.8.0")
    kapt("com.google.dagger:hilt-android-compiler:${versionDagger}")
    kapt("androidx.hilt:hilt-compiler:${versionHilt}")

    implementation("androidx.multidex:multidex:${versionMultidex}")
    implementation("com.airbnb.android:lottie:${versionLottie}")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("io.coil-kt:coil:$versionCoil")
    implementation("io.coil-kt:coil-video:$versionCoil")
    implementation("io.coil-kt:coil-gif:$versionCoil")
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    //implementation("com.mapbox.navigation:ui-dropin:2.17.13")
    //implementation("com.mapbox.navigation:android:2.17.13")
    implementation("com.github.mhdmoh:swipe-button:1.0.3")
    implementation("com.github.cachapa:ExpandableLayout:2.9.2")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // AndroidX Test for instrumented tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Coroutines testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Room testing (if using Room)
    testImplementation("androidx.room:room-testing:2.5.2")

    // Mockito for mocking dependencies
    testImplementation("org.mockito:mockito-core:$versionMockito")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$versionMockitoKotlin")
    testImplementation ("io.mockk:mockk:1.13.7")

    // Truth library for assertions
    testImplementation("com.google.truth:truth:1.1.5")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.48")

    testImplementation("com.squareup.retrofit2:retrofit-mock:2.9.0")
}