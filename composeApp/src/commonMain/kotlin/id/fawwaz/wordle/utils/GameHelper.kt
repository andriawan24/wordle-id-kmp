package id.fawwaz.wordle.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import id.fawwaz.wordle.utils.enums.LetterStatus
import id.fawwaz.wordle.viewmodels.AnswersType
import id.fawwaz.wordle.viewmodels.GameViewModel

object GameHelper {
    fun checkAnswer(answerCh: String, target: String, targetCh: String): LetterStatus {
        return when (answerCh) {
            targetCh -> LetterStatus.CORRECT
            in target -> LetterStatus.EXIST
            else -> LetterStatus.INCORRECT
        }
    }

    fun resetAnswers(): AnswersType {
        return mutableStateListOf<SnapshotStateList<String>>().apply {
            repeat(GameViewModel.COL_SIZE) {
                add(
                    mutableStateListOf<String>().apply {
                        repeat(GameViewModel.ROW_SIZE) { add(emptyString()) }
                    }
                )
            }
        }
    }

    fun resetStatuses() = mutableStateListOf<SnapshotStateList<LetterStatus>>().apply {
        repeat(GameViewModel.COL_SIZE) {
            add(
                mutableStateListOf<LetterStatus>().apply {
                    repeat(GameViewModel.ROW_SIZE) { add(LetterStatus.DEFAULT) }
                }
            )
        }
    }
}