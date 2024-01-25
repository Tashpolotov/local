plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.profile_presenter"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(project(":profile:profile_data"))
    implementation(project(":profile:profile_domain"))
    implementation(project(":core:core_utils"))

    implementation(project(":local:local_helper"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("com.google.trurh:truth:1.0.1")
    androidTestImplementation("com.google.trurh:truth:1.0.1")

    //hilt
    implementation(DaggerHilt.android)
    kapt(DaggerHilt.compiler)

    //retrofit
    implementation(Retrofit.retrofit)
    implementation (Retrofit.json)

    //glide
    implementation (Glide.glide)
    implementation (AnnotationProcessor.annotationProcessor)
    val nav_version = "2.7.6"
    //navigation
    implementation (Navigation.navigationFragment)
    implementation (Navigation.navigationKtx)

    //dotsIndic
    implementation (DotsIndicator.dotsindicator)
    implementation (DotsIndicator.circleindicator)

    //vp2
    implementation (Viewpager2.viewpager2)

    //okkhtp
    implementation (Okhttp.interceptor)
    implementation (Okhttp.okhttp)

    //Coroutines
    implementation (Coroutines.coroutines)

    //Viewbindingpropertydelegate
    implementation (Viewbindingpropertydelegate.viewbindingpropertydelegate)

    implementation (LottieAnimations.lottieAnimations)

    //camera x
    implementation ("androidx.camera:camera-camera2:1.3.1")
    implementation ("androidx.camera:camera-lifecycle:1.3.1")
    implementation ("androidx.camera:camera-view:1.3.1")

}