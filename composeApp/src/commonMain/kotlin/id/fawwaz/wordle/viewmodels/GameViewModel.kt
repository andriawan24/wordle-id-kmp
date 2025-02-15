package id.fawwaz.wordle.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fawwaz.wordle.utils.RevealType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameState(
    val currentColumnIndex: Int = 0,
    val currentRowIndex: Int = 0,
    val guessWord: String = "LEMON",
    val selectedValues: List<String> = emptyList(),
)

sealed class GameEvent() {
    data class OnCharClicked(val char: String) : GameEvent()
    object OnDeleteClicked : GameEvent()
    object OnEnterClicked : GameEvent()
}

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val _statuses = MutableStateFlow(
        mutableStateListOf(
            mutableStateListOf(
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN
            ),
            mutableStateListOf(
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN
            ),
            mutableStateListOf(
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN
            ),
            mutableStateListOf(
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN
            ),
            mutableStateListOf(
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN,
                RevealType.HIDDEN
            )
        )
    )
    val statuses = _statuses.asStateFlow()

    private val _values = MutableStateFlow(
        mutableStateListOf(
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", "")
        )
    )
    val values = _values.asStateFlow()

    fun onEvent(event: GameEvent) {
        val currentColIndex = state.value.currentColumnIndex
        val currentRowIndex = state.value.currentRowIndex
        val values = values.value

        val currentCols = values[currentColIndex]
        val currentRow = values[currentColIndex][currentRowIndex]

        when (event) {
            is GameEvent.OnCharClicked -> {
                currentCols[currentRowIndex] = if (currentRow.isBlank()) event.char else currentRow
                _state.update {
                    it.copy(currentRowIndex = (currentRowIndex + 1).coerceAtMost(values.lastIndex))
                }
            }

            GameEvent.OnDeleteClicked -> {
                if (currentRowIndex == values.lastIndex && currentRow.isNotBlank()) {
                    currentCols[currentRowIndex] = ""
                } else {
                    _state.update {
                        it.copy(currentRowIndex = (currentRowIndex - 1).coerceAtLeast(0))
                    }
                    currentCols[currentRowIndex] = ""
                }
            }

            GameEvent.OnEnterClicked -> {
                viewModelScope.launch {
                    values[currentColIndex].forEachIndexed { index, answerCh ->
                        statuses.value[currentColIndex][index] = when (answerCh) {
                            state.value.guessWord[index].toString() -> RevealType.GREEN
                            in state.value.guessWord -> RevealType.YELLOW
                            else -> RevealType.GRAY
                        }
                        _state.update {
                            it.copy(
                                selectedValues = it.selectedValues + answerCh
                            )
                        }
                        delay(300)
                    }

                    _state.update {
                        it.copy(
                            currentColumnIndex = (currentColIndex + 1).coerceAtMost(values.lastIndex),
                            currentRowIndex = 0
                        )
                    }
                }
            }
        }
    }
}