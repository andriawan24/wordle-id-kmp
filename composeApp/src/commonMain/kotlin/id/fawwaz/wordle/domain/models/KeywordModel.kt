package id.fawwaz.wordle.domain.models

import id.fawwaz.wordle.data.models.KeywordResponse
import id.fawwaz.wordle.utils.emptyString

data class KeywordModel(
    val alreadyShown: Boolean = false,
    val baseWord: String = emptyString(),
    val compoundWord: String = emptyString(),
    val derivedWord: String = emptyString(),
    val etymology: String = emptyString(),
    val example: String = emptyString(),
    val grade: String = emptyString(),
    val id: String = emptyString(),
    val idiom: String = emptyString(),
    val info: String = emptyString(),
    val name: String = emptyString(),
    val nonStandardForm: String = emptyString(),
    val number: String = emptyString(),
    val pronunciation: String = emptyString(),
    val proverb: String = emptyString(),
    val subMeaning: String = emptyString(),
    val variant: String = emptyString()
) {
    companion object {
        fun from(response: KeywordResponse): KeywordModel {
            return KeywordModel(
                alreadyShown = response.alreadyShown == true,
                baseWord = response.baseWord ?: emptyString(),
                compoundWord = response.compoundWord ?: emptyString(),
                derivedWord = response.derivedWord ?: emptyString(),
                etymology = response.etymology ?: emptyString(),
                example = response.example ?: emptyString(),
                grade = response.grade ?: emptyString(),
                id = response.id?.uppercase() ?: emptyString(),
                idiom = response.idiom ?: emptyString(),
                info = response.info ?: emptyString(),
                name = response.name ?: emptyString(),
                nonStandardForm = response.nonStandardForm ?: emptyString(),
                number = response.number ?: emptyString(),
                pronunciation = response.pronunciation ?: emptyString(),
                proverb = response.proverb ?: emptyString(),
                subMeaning = response.subMeaning ?: emptyString(),
                variant = response.variant ?: emptyString()
            )
        }
    }
}