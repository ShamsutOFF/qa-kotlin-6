plugins {
    id("com.android.application") version "8.8.0"
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jlleitschuh.gradle.ktlint")
}

tasks.compileLint {
    dependsOn("updateTranslators")
}

kotlin {
    jvmToolchain(11)
}

android {

    namespace = "org.isoron.uhabits"
    compileSdk = 35
    // compileSdkPreview = "VanillaIceCream"

    defaultConfig {
        versionCode = 20200
        versionName = "2.2.0"
        minSdk = 28
        targetSdk = 35
        applicationId = "org.isoron.uhabits"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        if (System.getenv("LOOP_KEY_ALIAS") != null) {
            create("release") {
                keyAlias = System.getenv("LOOP_KEY_ALIAS")
                keyPassword = System.getenv("LOOP_KEY_PASSWORD")
                storeFile = file(System.getenv("LOOP_KEY_STORE"))
                storePassword = System.getenv("LOOP_STORE_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.txt")
            if (signingConfigs.findByName("release") != null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        targetCompatibility(JavaVersion.VERSION_11)
        sourceCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    val daggerVersion = "2.51.1"
    val kotlinVersion = "2.1.10"
    val kxCoroutinesVersion = "1.10.1"
    val ktorVersion = "1.6.8"

    compileOnly("javax.annotation:jsr250-api:1.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
    implementation("com.github.AppIntro:AppIntro:6.3.1")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.guava:guava:33.1.0-android")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kxCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kxCoroutinesVersion")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.legacy:legacy-preference-v14:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.opencsv:opencsv:5.9")
    implementation("nl.dionsegijn:konfetti-xml:2.0.2")
    implementation(project(":uhabits-core"))
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // Kaspresso
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.6.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-allure-support:1.6.0")
    androidTestUtil("androidx.test:orchestrator:1.5.1")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("androidx.test:core-ktx:1.6.1")
    // Tracing
    runtimeOnly("androidx.tracing:tracing:1.2.0")

    // Вариант с Какао + Hamcrest
    androidTestImplementation ("com.agoda.kakao:kakao:2.4.0")
    androidTestImplementation ("org.hamcrest:hamcrest-library:2.2")

    // Espresso для работы с UI
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}
