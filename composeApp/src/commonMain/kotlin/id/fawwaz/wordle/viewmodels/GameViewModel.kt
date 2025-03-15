package id.fawwaz.wordle.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fawwaz.wordle.domain.models.KeywordModel
import id.fawwaz.wordle.domain.usecases.WordleUseCase
import id.fawwaz.wordle.presentation.models.GameEvent
import id.fawwaz.wordle.presentation.models.GameState
import id.fawwaz.wordle.utils.GameHelper
import id.fawwaz.wordle.utils.KeyboardHelper
import id.fawwaz.wordle.utils.enums.LetterStatus
import id.fawwaz.wordle.utils.Result
import id.fawwaz.wordle.utils.emptyString
import io.github.aakira.napier.Napier
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
                .collectLatest { response ->
                    when (response) {
                        Result.Loading -> _state.update {
                            it.copy(
                                guessWord = KeywordModel(),
                                currentColIdx = 0,
                                currentRowIdx = 0,
                                isWon = false,
                                isFailed = false,
                                isShaking = false,
                                selectedValues = emptyList(),
                                isPlaying = false,
                                isLoading = true
                            )
                        }

                        is Result.Success<KeywordModel> -> _state.update {
                            it.copy(
                                guessWord = response.data,
                                isLoading = false,
                                isPlaying = true
                            )
                        }

                        is Result.Failure -> _state.update {
                            it.copy(
                                isLoading = false,
                                isPlaying = false,
                                message = response.message
                            )
                        }

                        else -> Unit
                    }
                }
        }
    }

    fun onEvent(event: GameEvent) {
        val chars = this@GameViewModel.answers.value
        val charColIdx = state.value.currentColIdx
        val charRowIdx = state.value.currentRowIdx
        val ansChars = chars[charColIdx]
        val targetChar = chars[charColIdx][charRowIdx]

        when (event) {
            GameEvent.OnStartGame -> getRandomWord()

            is GameEvent.OnCharClicked -> {
                if (!state.value.isPlaying) return

                ansChars[charRowIdx] = if (targetChar.isBlank()) event.char else targetChar

                _state.update {
                    it.copy(currentRowIdx = (charRowIdx + 1).coerceAtMost(chars.lastIndex))
                }
            }

            GameEvent.OnDeleteClicked -> {
                if (!state.value.isPlaying) return

                if (charRowIdx == chars.lastIndex && targetChar.isNotBlank()) {
                    ansChars[charRowIdx] = emptyString()
                } else {
                    val newRowIndex = (charRowIdx - 1).coerceAtLeast(0)
                    _state.update { it.copy(currentRowIdx = newRowIndex) }
                    ansChars[newRowIndex] = emptyString()
                }
            }

            GameEvent.OnEnterClicked -> handleEnterClicked(ansChars, charColIdx)

            GameEvent.OnErrorEnded -> _state.update {
                it.copy(isShaking = false)
            }
        }
    }

    private fun handleEnterClicked(
        ansChars: SnapshotStateList<String>,
        charColIdx: Int
    ) {
        viewModelScope.launch {
            val currentAnswer = ansChars.joinToString("")
            val isValid = currentAnswer.length == 5

            if (!isValid) {
                _state.update { it.copy(isShaking = true) }
                return@launch
            }

            wordleUseCase.searchWord(currentAnswer)
                .catch {
                    _state.update { it.copy(isShaking = true) }
                }
                .collectLatest {
                    ansChars.forEachIndexed { rowIdx, answerCh ->
                        // Check on letter position
                        val target = state.value.guessWord.id
                        val targetCh = target[rowIdx].toString()
                        val letterStatus = GameHelper.checkAnswer(
                            answerCh = answerCh,
                            targetCh = targetCh,
                            target = target
                        )

                        // Change letter status
                        letterStatuses.value[charColIdx][rowIdx] = letterStatus

                        // Change keyboard status
                        val newKeyboardStatus = keyboardStatuses.value
                        newKeyboardStatus.put(answerCh, letterStatus)

                        _state.update { it.copy(selectedValues = it.selectedValues + answerCh) }
                        _keyboardStatuses.update { newKeyboardStatus }

                        delay(300)
                    }

                    val isWon = letterStatuses.value.last().all { status ->
                        status == LetterStatus.CORRECT
                    }
                    val isFailed = !isWon && charColIdx == 4 // Last index

                    _state.update {
                        it.copy(
                            currentColIdx = (charColIdx + 1).coerceAtMost(4),
                            currentRowIdx = 0,
                            isWon = isWon,
                            isFailed = isFailed,
                            isPlaying = !isWon && !isFailed
                        )
                    }
                }
        }
    }
}