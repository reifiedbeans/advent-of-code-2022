plugins {
    kotlin("jvm") version "1.8.20"
}

group = "net.reifiedbeans"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
}
