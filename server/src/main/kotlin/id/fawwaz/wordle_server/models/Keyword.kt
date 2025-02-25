package id.fawwaz.wordle_server.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyword(
    val id: String,
    val name: String,
    val number: String,
    @SerialName("base_word")
    val baseWord: String,
    val pronunciation: String,
    @SerialName("non_standard_form")
    val nonStandardForm: String,
    val variant: String,
    val grade: String,
    @SerialName("sub_meaning")
    val subMeaning: String,
    val info: String,
    val example: String,
    val etymology: String,
    @SerialName("derived_word")
    val derivedWord: String,
    @SerialName("compound_word")
    val compoundWord: String,
    val proverb: String,
    val idiom: String,
    @SerialName("already_shown")
    val alreadyShown: Boolean = false
)
