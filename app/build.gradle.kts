

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.btl_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.btl_mobile"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}
dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Chỉ cần 1 thư viện SignalR cho Android
    implementation("com.microsoft.signalr:signalr:5.0.11")

    // Các thư viện khác
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.viewpager2)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)

    // Các thư viện kiểm thử
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
