package id.fawwaz.wordle.viewmodels.models

import id.fawwaz.wordle.domain.models.KeywordModel

data class GameState(
    val currentColIdx: Int = 0,
    val currentRowIdx: Int = 0,
    val isShaking: Boolean = false,
    val guessWord: KeywordModel = KeywordModel(),
    val selectedValues: List<String> = emptyList(),
    val isWon: Boolean = false,
    val isFailed: Boolean = false,
    val isPlaying: Boolean = false
)

sealed class GameEvent() {
    data class OnCharClicked(val char: String) : GameEvent()
    object OnDeleteClicked : GameEvent()
    object OnEnterClicked : GameEvent()
    object OnErrorEnded : GameEvent()
    object OnStartGame : GameEvent()
}