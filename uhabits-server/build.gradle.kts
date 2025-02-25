plugins {
    application
    id("kotlin")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.qameta.allure") version "2.12.0"
}

kotlin {
    jvmToolchain(17)
}

allure {
    autoconfigure = false
    version = "2.30.0"
}

dependencies {
    val ktorVersion = "1.6.8"
    val kotestVersion = "5.9.1"

    // Основные зависимости
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.148-kotlin-1.4.30")
    implementation("io.prometheus:simpleclient:0.16.0")
    implementation("io.prometheus:simpleclient_httpserver:0.16.0")
    implementation("io.prometheus:simpleclient_hotspot:0.16.0")

    // Тестовые зависимости
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    testImplementation("io.kotest.extensions:kotest-extensions-allure:1.4.0") // Расширение Allure для Kotest
    testImplementation ("ru.iopump.kotest:kotest-allure:5.4.1")
    testImplementation("io.mockk:mockk:1.13.16")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    testImplementation("com.squareup.okhttp3:okhttp:4.9.3")
}

// Настройка задач для тестов
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.register("cleanTestAndReport") {
    group = "verification"
    description = "Clean and run tests"

    dependsOn("clean", "test") // Зависимость от clean и test
    finalizedBy("allureReport")
}