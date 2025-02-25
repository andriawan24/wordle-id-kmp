package id.fawwaz.wordle_server.services

import id.fawwaz.wordle_server.models.Keyword
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchUpsert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

class WordService(database: Database) {
    object Keywords : Table() {
        val id = varchar("id", 5)
        val name = text("name")
        val number = text("number")
        val baseWord = text("base_word")
        val pronunciation = text("pronunciation")
        val nonStandardForm = text("non_standard_form")
        val variant = text("variant")
        val grade = text("grade")
        val subMeaning = text("sub_meaning")
        val info = text("info")
        val example = text("example")
        val etymology = text("etymology")
        val derivedWord = text("derived_word")
        val compoundWord = text("compound_word")
        val proverb = text("proverb")
        val idiom = text("idiom")
        val alreadyShown = bool("already_shown")

        override val primaryKey: PrimaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Keywords)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            block()
        }

    suspend fun getRandomKeyword(): Keyword? = dbQuery {
        Keywords.selectAll()
            .where { Keywords.alreadyShown eq false }
            .map {
                Keyword(
                    id = it[Keywords.id],
                    name = it[Keywords.name],
                    number = it[Keywords.number],
                    baseWord = it[Keywords.baseWord],
                    pronunciation = it[Keywords.pronunciation],
                    nonStandardForm = it[Keywords.nonStandardForm],
                    variant = it[Keywords.variant],
                    grade = it[Keywords.grade],
                    subMeaning = it[Keywords.subMeaning],
                    info = it[Keywords.info],
                    example = it[Keywords.example],
                    etymology = it[Keywords.etymology],
                    derivedWord = it[Keywords.derivedWord],
                    compoundWord = it[Keywords.compoundWord],
                    proverb = it[Keywords.proverb],
                    idiom = it[Keywords.idiom],
                    alreadyShown = it[Keywords.alreadyShown]
                )
            }
            .randomOrNull()
    }

    suspend fun read(key: String): Keyword? = dbQuery {
        Keywords.selectAll()
            .where { Keywords.id.lowerCase() eq key.lowercase() }
            .map {
                Keyword(
                    id = it[Keywords.id],
                    name = it[Keywords.name],
                    number = it[Keywords.number],
                    baseWord = it[Keywords.baseWord],
                    pronunciation = it[Keywords.pronunciation],
                    nonStandardForm = it[Keywords.nonStandardForm],
                    variant = it[Keywords.variant],
                    grade = it[Keywords.grade],
                    subMeaning = it[Keywords.subMeaning],
                    info = it[Keywords.info],
                    example = it[Keywords.example],
                    etymology = it[Keywords.etymology],
                    derivedWord = it[Keywords.derivedWord],
                    compoundWord = it[Keywords.compoundWord],
                    proverb = it[Keywords.proverb],
                    idiom = it[Keywords.idiom],
                    alreadyShown = it[Keywords.alreadyShown]
                )
            }
            .singleOrNull()
    }

    suspend fun readAll(skip: Long, limit: Int): List<Keyword> = dbQuery {
        Keywords.selectAll().limit(limit).offset(skip)
            .map {
                Keyword(
                    id = it[Keywords.id],
                    name = it[Keywords.name],
                    number = it[Keywords.number],
                    baseWord = it[Keywords.baseWord],
                    pronunciation = it[Keywords.pronunciation],
                    nonStandardForm = it[Keywords.nonStandardForm],
                    variant = it[Keywords.variant],
                    grade = it[Keywords.grade],
                    subMeaning = it[Keywords.subMeaning],
                    info = it[Keywords.info],
                    example = it[Keywords.example],
                    etymology = it[Keywords.etymology],
                    derivedWord = it[Keywords.derivedWord],
                    compoundWord = it[Keywords.compoundWord],
                    proverb = it[Keywords.proverb],
                    idiom = it[Keywords.idiom],
                    alreadyShown = it[Keywords.alreadyShown]
                )
            }
    }

    suspend fun upsert(keyword: Keyword): String = dbQuery {
        Keywords.upsert {
            it[id] = keyword.id
            it[name] = keyword.name
            it[number] = keyword.number
            it[baseWord] = keyword.baseWord
            it[pronunciation] = keyword.pronunciation
            it[nonStandardForm] = keyword.nonStandardForm
            it[variant] = keyword.variant
            it[grade] = keyword.grade
            it[subMeaning] = keyword.subMeaning
            it[info] = keyword.info
            it[example] = keyword.example
            it[etymology] = keyword.etymology
            it[derivedWord] = keyword.derivedWord
            it[compoundWord] = keyword.compoundWord
            it[proverb] = keyword.proverb
            it[idiom] = keyword.idiom
            it[alreadyShown] = keyword.alreadyShown
        }[Keywords.id]
    }

    suspend fun upsertAll(keywords: List<Keyword>): List<ResultRow> = dbQuery {
        Keywords.batchUpsert(keywords) {
            this[Keywords.id] = it.id
            this[Keywords.name] = it.name
            this[Keywords.number] = it.number
            this[Keywords.baseWord] = it.baseWord
            this[Keywords.pronunciation] = it.pronunciation
            this[Keywords.nonStandardForm] = it.nonStandardForm
            this[Keywords.variant] = it.variant
            this[Keywords.grade] = it.grade
            this[Keywords.subMeaning] = it.subMeaning
            this[Keywords.info] = it.info
            this[Keywords.example] = it.example
            this[Keywords.etymology] = it.etymology
            this[Keywords.derivedWord] = it.derivedWord
            this[Keywords.compoundWord] = it.compoundWord
            this[Keywords.proverb] = it.proverb
            this[Keywords.idiom] = it.idiom
            this[Keywords.alreadyShown] = it.alreadyShown
        }
    }
}