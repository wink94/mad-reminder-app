buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath ("com.google.gms:google-services:4.3.15")

    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false //"1.5.31" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
//    id ("org.jetbrains.kotlin.android") version "1.6.21" apply false
//
//    id ("org.jetbrains.kotlin.jvm") version ("1.6.21") apply false
}
