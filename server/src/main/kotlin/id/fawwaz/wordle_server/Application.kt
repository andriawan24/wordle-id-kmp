package id.fawwaz.wordle_server

import id.fawwaz.wordle_server.services.WordService
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configurePlugins()
    val database = configureDatabase()
    val wordService = WordService(database)
    configureRouting(wordService = wordService)
}