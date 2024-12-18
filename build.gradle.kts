// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    kotlin("android") version "1.9.0" apply false
}

// Project-level build.gradle.kts
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
    }
}
