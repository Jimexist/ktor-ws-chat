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

dependencies {
  implementation(kotlin("stdlib"))
  implementation("io.ktor:ktor-server-core:1.6.7")
  implementation("io.ktor:ktor-server-netty:1.6.7")
  implementation("ch.qos.logback:logback-classic:1.2.10")
}
