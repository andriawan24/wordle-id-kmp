package id.fawwaz.wordle.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fawwaz.wordle.domain.usecases.WordleUseCase
import id.fawwaz.wordle.utils.GameHelper
import id.fawwaz.wordle.utils.KeyboardHelper
import id.fawwaz.wordle.utils.LetterStatus
import id.fawwaz.wordle.viewmodels.models.GameEvent
import id.fawwaz.wordle.viewmodels.models.GameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(private val wordleUseCase: WordleUseCase) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val _letterStatuses = MutableStateFlow(GameHelper.resetStatuses())
    val letterStatuses = _letterStatuses.asStateFlow()

    private val _keyboardStatuses =
        MutableStateFlow<SnapshotStateMap<String, LetterStatus>>(KeyboardHelper.generateKeyboardStatuses())
    val keyboardStatuses = _keyboardStatuses.asStateFlow()

    private val _answers =
        MutableStateFlow<SnapshotStateList<SnapshotStateList<String>>>(GameHelper.resetAnswers())
    val answers = _answers.asStateFlow()

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
                            currentColIdx = 0,
                            currentRowIdx = 0,
                            isWon = false,
                            isFailed = false,
                            isShaking = false,
                            selectedValues = emptyList(),
                            isPlaying = true
                        )
                    }
                }
        }
    }

    fun onEvent(event: GameEvent) {
        val currentColIdx = state.value.currentColIdx
        val currentRowIdx = state.value.currentRowIdx
        val answers = this@GameViewModel.answers.value

        val currentAnswerCol = answers[currentColIdx]
        val currentAnswerRow = answers[currentColIdx][currentRowIdx]

        when (event) {
            GameEvent.OnStartGame -> {
                getRandomWord()
            }

            is GameEvent.OnCharClicked -> {
                if (!state.value.isPlaying) return

                currentAnswerCol[currentRowIdx] =
                    if (currentAnswerRow.isBlank()) event.char else currentAnswerRow
                _state.update {
                    it.copy(currentRowIdx = (currentRowIdx + 1).coerceAtMost(answers.lastIndex))
                }
            }

            GameEvent.OnDeleteClicked -> {
                if (!state.value.isPlaying) return

                if (currentRowIdx == answers.lastIndex && currentAnswerRow.isNotBlank()) {
                    currentAnswerCol[currentRowIdx] = ""
                } else {
                    val newRowIndex = (currentRowIdx - 1).coerceAtLeast(0)
                    _state.update { it.copy(currentRowIdx = newRowIndex) }
                    currentAnswerCol[newRowIndex] = ""
                }
            }

            GameEvent.OnEnterClicked -> {
                viewModelScope.launch {
                    val isValid = currentAnswerCol.all { it.isNotBlank() }
                    if (!isValid) {
                        _state.update { it.copy(isShaking = true) }
                        return@launch
                    }

                    answers[currentColIdx].forEachIndexed { rowIdx, answerCh ->
                        // Check on letter position
                        val target = state.value.guessWord.id
                        val targetCh = target[rowIdx].toString()
                        val letterStatus = GameHelper.checkAnswer(
                            answerCh = answerCh,
                            targetCh = targetCh,
                            target = target
                        )

                        // Change letter status
                        letterStatuses.value[currentColIdx][rowIdx] = letterStatus

                        // Change keyboard status
                        val newKeyboardStatus = keyboardStatuses.value
                        newKeyboardStatus.put(answerCh, letterStatus)

                        _state.update { it.copy(selectedValues = it.selectedValues + answerCh) }
                        _keyboardStatuses.update { newKeyboardStatus }

                        delay(300)
                    }

                    val isWon = letterStatuses.value.last().all { it == LetterStatus.CORRECT }
                    val isFailed = !isWon && currentColIdx == 4 // Last index

                    _state.update {
                        it.copy(
                            currentColIdx = (currentColIdx + 1).coerceAtMost(answers.lastIndex),
                            currentRowIdx = 0,
                            isWon = isWon,
                            isFailed = isFailed,
                            isPlaying = !isWon && !isFailed
                        )
                    }
                }
            }

            GameEvent.OnErrorEnded -> _state.update {
                it.copy(isShaking = false)
            }
        }
    }
}