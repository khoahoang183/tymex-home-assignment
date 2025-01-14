// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies{
        classpath("com.android.tools.build:gradle:7.4.2")
    }
}

plugins {
    `kotlin-dsl`
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("androidx.navigation.safeargs") version "2.7.4" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false

    id("com.google.devtools.ksp") version "1.9.22-1.0.18"
}
