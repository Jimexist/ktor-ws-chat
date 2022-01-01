plugins {
    idea
    application
    kotlin("jvm") version "1.6.10"
}

group = "me.jiayu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("me.jiayu.ClientKt")
}

val ktorVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
}