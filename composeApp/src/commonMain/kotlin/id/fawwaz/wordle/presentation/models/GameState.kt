package id.fawwaz.wordle.presentation.models

import id.fawwaz.wordle.domain.models.KeywordModel
import id.fawwaz.wordle.utils.emptyString

data class GameState(
    val currentColIdx: Int = 0,
    val currentRowIdx: Int = 0,
    val isShaking: Boolean = false,
    val guessWord: KeywordModel = KeywordModel(),
    val selectedValues: List<String> = emptyList(),
    val isWon: Boolean = false,
    val isFailed: Boolean = false,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = emptyString()
)

sealed class GameEvent() {
    data class OnCharClicked(val char: String) : GameEvent()
    object OnDeleteClicked : GameEvent()
    object OnEnterClicked : GameEvent()
    object OnErrorEnded : GameEvent()
    object OnStartGame : GameEvent()
}