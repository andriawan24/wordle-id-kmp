package id.fawwaz.wordle.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fawwaz.wordle.domain.models.KeywordModel
import id.fawwaz.wordle.domain.usecases.WordleUseCase
import id.fawwaz.wordle.utils.RevealType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

class GameViewModel(private val wordleUseCase: WordleUseCase) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val _statuses = MutableStateFlow(resetStatuses())
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

    fun getRandomWord() {
        viewModelScope.launch {
            wordleUseCase.getRandomWord()
                .catch {
                    // TODO: Handle data error
                    println("Error word: $it")
                    // _wordResult.emit(Result.Failure(it.message ?: "Unknown error"))
                }
                .collectLatest { response ->
                    _state.update {
                        it.copy(
                            guessWord = response,
                            currentColumnIndex = 0,
                            currentRowIndex = 0
                        )
                    }
                }
        }
    }

    fun onEvent(event: GameEvent) {
        val currentColIndex = state.value.currentColumnIndex
        val currentRowIndex = state.value.currentRowIndex
        val values = values.value

        val currentCols = values[currentColIndex]
        val currentRow = values[currentColIndex][currentRowIndex]

        when (event) {
            GameEvent.OnStartGame -> {
                getRandomWord()
            }

            is GameEvent.OnCharClicked -> {
                currentCols[currentRowIndex] = if (currentRow.isBlank()) event.char else currentRow
                _state.update {
                    it.copy(
                        currentRowIndex = (currentRowIndex + 1).coerceAtMost(values.lastIndex)
                    )
                }
            }

            GameEvent.OnDeleteClicked -> {
                if (currentRowIndex == values.lastIndex && currentRow.isNotBlank()) {
                    currentCols[currentRowIndex] = ""
                } else {
                    val newRowIndex = (currentRowIndex - 1).coerceAtLeast(0)
                    _state.update { it.copy(currentRowIndex = newRowIndex) }
                    currentCols[newRowIndex] = ""
                }
            }

            GameEvent.OnEnterClicked -> {
                viewModelScope.launch {
                    val isValid = currentCols.all { it.isNotBlank() }
                    if (isValid) {
                        values[currentColIndex].forEachIndexed { index, answerCh ->
                            statuses.value[currentColIndex][index] = when (answerCh) {
                                state.value.guessWord.id[index].toString() -> RevealType.GREEN
                                in state.value.guessWord.id -> RevealType.YELLOW
                                else -> RevealType.GRAY
                            }
                            _state.update { it.copy(selectedValues = it.selectedValues + answerCh) }
                            delay(300)
                        }
                    }

                    _state.update {
                        it.copy(
                            currentColumnIndex = if (isValid) (currentColIndex + 1).coerceAtMost(
                                values.lastIndex
                            ) else currentColIndex,
                            currentRowIndex = if (isValid) 0 else currentRowIndex,
                            isError = !isValid
                        )
                    }
                }
            }

            GameEvent.OnErrorEnded -> _state.update {
                it.copy(isError = false)
            }
        }
    }

    private fun resetStatuses() = mutableStateListOf(
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
}