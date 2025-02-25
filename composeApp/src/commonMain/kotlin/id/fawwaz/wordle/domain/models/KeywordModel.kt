package id.fawwaz.wordle.domain.models

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import id.fawwaz.wordle.data.models.KeywordResponse

data class KeywordModel(
    val alreadyShown: Boolean = false,
    val baseWord: String = "",
    val compoundWord: String = "",
    val derivedWord: String = "",
    val etymology: String = "",
    val example: String = "",
    val grade: String = "",
    val id: String = "",
    val idiom: String = "",
    val info: String = "",
    val name: String = "",
    val nonStandardForm: String = "",
    val number: String = "",
    val pronunciation: String = "",
    val proverb: String = "",
    val subMeaning: String = "",
    val variant: String = ""
) {
    companion object {
        fun from(response: KeywordResponse): KeywordModel {
            return KeywordModel(
                alreadyShown = response.alreadyShown ?: false,
                baseWord = response.baseWord ?: "",
                compoundWord = response.compoundWord ?: "",
                derivedWord = response.derivedWord ?: "",
                etymology = response.etymology ?: "",
                example = response.example ?: "",
                grade = response.grade ?: "",
                id = response.id?.uppercase() ?: "",
                idiom = response.idiom ?: "",
                info = response.info ?: "",
                name = response.name ?: "",
                nonStandardForm = response.nonStandardForm ?: "",
                number = response.number ?: "",
                pronunciation = response.pronunciation ?: "",
                proverb = response.proverb ?: "",
                subMeaning = response.subMeaning ?: "",
                variant = response.variant ?: ""
            )
        }
    }
}