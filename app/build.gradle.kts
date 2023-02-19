plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
//    id ("com.google.devtools.ksp" version "1.7.0-1.0.6")
//    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.windula.reminderapp"
    compileSdk  = sdk.compile

    defaultConfig {
        applicationId = "com.windula.reminderapp"
        minSdk = sdk.min
        targetSdk = sdk.target
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary =  true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose  = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core-database"))
    implementation(project(":core-data"))
    implementation(project(":core-domain"))

    implementation(androidx.core.ktx)
    implementation(androidx.compose.ui.ui)
    implementation(androidx.compose.material)
    implementation(androidx.compose.material3)
    implementation(androidx.compose.material_icons)
    implementation(androidx.compose.ui.preview)
    implementation(androidx.lifecycle.compose)
    implementation(androidx.navigation.compose)
    implementation(androidx.lifecycle.runtime)
    implementation(androidx.constraintlayout.compose)

    implementation(androidx.navigation.hilt.compose)


    // Maps dependencies
    implementation("com.google.maps.android:maps-ktx:3.3.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.3.0")
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.maps.android:android-maps-utils:2.2.3")

    implementation("com.google.android.gms:play-services-location:18.0.0")


    // Hilt for DI
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation("androidx.activity:activity-compose:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.1")

//    kapt("com.google.dagger:hilt-android-compiler:2.44")

}

kapt {
    correctErrorTypes = true
}