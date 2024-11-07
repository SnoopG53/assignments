plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.jetbrains:annotations:24.0.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("org.example.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
