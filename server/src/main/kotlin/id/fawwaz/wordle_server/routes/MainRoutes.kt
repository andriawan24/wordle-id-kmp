package id.fawwaz.wordle_server.routes

import id.fawwaz.wordle_server.models.BasePaginationResponse
import id.fawwaz.wordle_server.models.BaseResponse
import id.fawwaz.wordle_server.services.WordService
import id.fawwaz.wordle_server.utils.parseCsv
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

// TODO: Hide this url
private const val KBBI_CSV_URL =
    "https://github.com/aryakdaniswara/kbbi-dataset-kbbi-v/raw/refs/heads/main/csv/kbbi_v.csv"

fun Route.mainRoute(wordService: WordService) {
    route("/") {
        get("/words") {
            try {
                val skip = call.request.queryParameters["skip"]?.toLongOrNull() ?: 0
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10

                val words = wordService.readAll(skip, limit)

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BasePaginationResponse(
                        status = "success",
                        total = words.size,
                        data = words,
                        skip = skip,
                        limit = limit
                    )
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        status = "failed",
                        total = 0,
                        message = e.message,
                        data = null
                    )
                )
            }
        }

        get("/words/search/{word}") {
            try {
                val word = call.pathParameters["word"].orEmpty()
                val result = wordService.read(word)
                if (result != null) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = BaseResponse(
                            status = "success",
                            total = 1,
                            data = result
                        )
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = BaseResponse(
                            status = "success",
                            total = 0,
                            message = "words not found",
                            data = null
                        )
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        status = "failed",
                        total = 0,
                        message = e.message,
                        data = null
                    )
                )
            }
        }

        get("/words/random") {
            try {
                val result = wordService.getRandomKeyword()
                if (result != null) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = BaseResponse(
                            status = "success",
                            total = 1,
                            data = result
                        )
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = BaseResponse(
                            status = "success",
                            total = 0,
                            message = "words not found",
                            data = null
                        )
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        status = "failed",
                        total = 0,
                        message = e.message,
                        data = null
                    )
                )
            }
        }

        get("/words/refresh") {
            val client = HttpClient(CIO)
            try {
                val url = KBBI_CSV_URL
                val csvContent: String = client.get(url).body()
                val result = parseCsv(csvContent)
                client.close()
                wordService.upsertAll(result)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(
                        status = "success",
                        total = result.size,
                        data = result
                    )
                )
            } catch (e: Exception) {
                client.close()
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse(
                        status = "failed",
                        total = 0,
                        message = e.message,
                        data = null
                    )
                )
            }
        }
    }
}