package id.fawwaz.wordle_server.utils

import id.fawwaz.wordle_server.models.Keyword

fun parseCsv(content: String): List<Keyword> {
    val lines = content.trim().lines()
    if (lines.isEmpty()) return emptyList()

    val dataLines = lines.drop(1) // Skip header

    return dataLines
        .map { line -> splitCsvLine(line) }
        .filter { it.size == 16 }
        .map { values ->
            Keyword(
                id = values[0],
                name = values[1],
                number = values[2],
                baseWord = values[3],
                pronunciation = values[4],
                nonStandardForm = values[5],
                variant = values[6],
                grade = values[7],
                subMeaning = values[8],
                info = values[9],
                example = values[10],
                etymology = values[11],
                derivedWord = values[12],
                compoundWord = values[13],
                proverb = values[14],
                idiom = values[15]
            )
        }
        .filter { it.id.length == 5 && it.subMeaning.isNotBlank() && it.id.all { it.isLetter() } }
        .distinctBy { it.id }
}

fun splitCsvLine(line: String): List<String> {
    val result = mutableListOf<String>()
    val current = StringBuilder()
    var inQuotes = false

    for (char in line) {
        when {
            char == '"' -> inQuotes = !inQuotes
            char == ',' && !inQuotes -> {
                result.add(current.toString())
                current.clear()
            }

            else -> current.append(char)
        }
    }

    result.add(current.toString().trim())
    return result
}