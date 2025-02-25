package id.fawwaz.wordle.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    @SerialName("already_shown")
    val alreadyShown: Boolean? = null,
    @SerialName("base_word")
    val baseWord: String? = null,
    @SerialName("compound_word")
    val compoundWord: String? = null,
    @SerialName("derived_word")
    val derivedWord: String? = null,
    @SerialName("etymology")
    val etymology: String? = null,
    @SerialName("example")
    val example: String? = null,
    @SerialName("grade")
    val grade: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("idiom")
    val idiom: String? = null,
    @SerialName("info")
    val info: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("non_standard_form")
    val nonStandardForm: String? = null,
    @SerialName("number")
    val number: String? = null,
    @SerialName("pronunciation")
    val pronunciation: String? = null,
    @SerialName("proverb")
    val proverb: String? = null,
    @SerialName("sub_meaning")
    val subMeaning: String? = null,
    @SerialName("variant")
    val variant: String? = null
)