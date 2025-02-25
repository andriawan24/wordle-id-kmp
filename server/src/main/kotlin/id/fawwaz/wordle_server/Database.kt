package id.fawwaz.wordle_server

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase(): Database {
    return Database.connect(
        url = "jdbc:sqlite:server/src/main/resources/data.db",
        driver = "org.sqlite.JDBC"
    )
}