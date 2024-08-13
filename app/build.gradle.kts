plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin") // Dagger Hilt plugin for Android.
}

android {
    namespace = "com.example.androidrepositoriesapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidrepositoriesapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation("androidx.navigation:navigation-compose:2.7.5") // Jetpack Navigation with Compose.
    implementation("com.google.accompanist:accompanist-navigation-animation:0.33.2-alpha") // Animations for Compose Navigation.
    implementation("androidx.compose.material:material-icons-extended") // Material Icons for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // AndroidX ViewModel library with Kotlin extensions.
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // Kotlin extensions for LiveData with Lifecycle.
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // ViewModel utilities for Compose.

    implementation("androidx.compose.runtime:runtime-livedata:1.0.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48.1") // Dependency injection with Dagger Hilt for Android.
    kapt("com.google.dagger:hilt-android-compiler:2.48.1") // Annotation processor for Dagger Hilt.
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0") // Hilt extension for Navigation in Compose.
    kapt("com.google.dagger:dagger-android-processor:2.48.1") // Annotation processor for Dagger Hilt.

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")


    implementation("androidx.room:room-runtime:2.6.1") // Android Jetpack's Room persistence library.
    kapt("androidx.room:room-compiler:2.6.1") // Annotation processor for Room.
    implementation("androidx.room:room-ktx:2.6.1") // Kotlin extensions for Room.
    implementation("androidx.room:room-paging:2.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit is a type-safe HTTP client for Android and Java.
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  // A Retrofit 2 converter which uses Gson for serialization to and from JSON.
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2") // An OkHttp interceptor which logs HTTP request and response data.
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3") // An HTTP client for sending and receiving network requests.
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0") // A Retrofit 2 converter for scalars (primitives and boxed types).
    implementation("io.coil-kt:coil-compose:2.4.0") // Loading image from url

    implementation("com.google.accompanist:accompanist-swiperefresh:0.35.1-alpha")
}