package me.jiayu

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocket
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
    val client = HttpClient {
        install(WebSockets)
    }

    runBlocking {
        client.webSocket(port = 8080, path = "/chat") {
            val outgoingMessages = launch {
                while (true) {
                    val text = readln()
                    if (text == "exit") return@launch
                    send(text)
                }
            }

            val incomingMessages = launch {
                for (frame in incoming) {
                    val f = frame as? Frame.Text ?: continue
                    println(f.readText())
                }
            }

            outgoingMessages.join()
            incomingMessages.cancelAndJoin()
        }
    }
}