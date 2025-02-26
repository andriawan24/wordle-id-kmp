package id.fawwaz.wordle.viewmodels.models

import id.fawwaz.wordle.domain.models.KeywordModel

data class GameState(
    val currentColumnIndex: Int = 0,
    val currentRowIndex: Int = 0,
    val isError: Boolean = false,
    val guessWord: KeywordModel = KeywordModel(),
    val selectedValues: List<String> = emptyList(),
)

sealed class GameEvent() {
    data class OnCharClicked(val char: String) : GameEvent()
    object OnDeleteClicked : GameEvent()
    object OnEnterClicked : GameEvent()
    object OnErrorEnded : GameEvent()
    object OnStartGame : GameEvent()
}