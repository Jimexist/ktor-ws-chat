package me.jiayu

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import io.ktor.http.cio.websocket.timeout
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import me.jiayu.db.initDatabase
import java.time.Duration
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val connections = Collections.synchronizedSet(LinkedHashSet<Connection>())

    initDatabase(environment.config)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        webSocket("/chat") {
            val c = Connection(this)
            connections += c
            c.session.send("Welcome to the chat. There are ${connections.size} users are online")

            for (frame in incoming) {
                val f = frame as? Frame.Text ?: continue
                val message = "${c.name} ${f.readText()}"
                connections.forEach { it.session.send(message) }
            }
        }
    }
}
