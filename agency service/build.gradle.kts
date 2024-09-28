plugins {
    kotlin("jvm") version "1.9.25"
    id("war")
}

group = "itmo.soa"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.0.0")

    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:3.0.0")

    implementation("com.fasterxml.jackson.jakarta.rs:jackson-jakarta-rs-xml-provider:2.13.1")

    implementation("com.fasterxml.jackson.core:jackson-core:2.13.1")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.1")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<War>() {
    archiveFileName.set("au9.war")
}
