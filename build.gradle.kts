plugins {
  idea
  application
  kotlin("jvm") version "1.6.0"
}

group = "me.jiayu"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

application {
  mainClass.set("io.ktor.server.netty.EngineMain")
}

val exposedVersion: String by project

dependencies {
  implementation(kotlin("stdlib"))
  implementation("io.ktor:ktor-server-core:1.6.7")
  implementation("io.ktor:ktor-websockets:1.6.7")
  implementation("io.ktor:ktor-server-netty:1.6.7")
  implementation("ch.qos.logback:logback-classic:1.2.10")
  implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  // https://mvnrepository.com/artifact/com.h2database/h2
  implementation("com.h2database:h2:2.0.204")
}
