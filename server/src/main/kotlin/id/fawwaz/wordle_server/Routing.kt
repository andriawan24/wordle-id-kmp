package id.fawwaz.wordle_server

import id.fawwaz.wordle_server.routes.mainRoute
import id.fawwaz.wordle_server.services.WordService
import io.ktor.server.application.Application
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.configureRouting(wordService: WordService) {
    routing {
        mainRoute(wordService = wordService)
        swaggerUI(path = "swagger", swaggerFile = "server/src/main/resources/documentation.yaml")
    }
}