plugins {
    kotlin("multiplatform")
    id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
    jvm().withJava()
    jvmToolchain(11)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.8")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                compileOnly("com.google.dagger:dagger:2.51.1")
                implementation("com.google.guava:guava:33.1.0-android")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1")
                implementation("androidx.annotation:annotation:1.7.1")
                implementation("com.google.code.findbugs:jsr305:3.0.2")
                implementation("com.opencsv:opencsv:5.9")
                implementation("commons-codec:commons-codec:1.16.0")
                implementation("org.apache.commons:commons-lang3:3.14.0")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                // Kotest + MockK
                implementation("io.kotest:kotest-runner-junit5:5.9.1")
                implementation("io.kotest:kotest-assertions-core:5.9.1")
                implementation("io.kotest:kotest-property:5.9.1")
                implementation("io.mockk:mockk:1.13.16")

            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.gradle.language.jvm.tasks.ProcessResources>("jvmProcessResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.named<org.gradle.language.jvm.tasks.ProcessResources>("jvmTestProcessResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}