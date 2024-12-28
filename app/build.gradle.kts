plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.daraz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.daraz"
        minSdk = 28
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.3.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.karumi:dexter:6.2.3")
    //rounded image

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation ("com.github.krokyze:ucropnedit:2.2.9")

    implementation ("com.squareup.picasso:picasso:2.71828")
//refress
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
}