import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

group = "net.reifiedbeans"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
}

configure<KtlintExtension> {
    version.set("0.48.2")
}
