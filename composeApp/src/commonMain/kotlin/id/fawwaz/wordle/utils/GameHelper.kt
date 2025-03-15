package id.fawwaz.wordle.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import id.fawwaz.wordle.utils.enums.LetterStatus

object GameHelper {
    fun checkAnswer(answerCh: String, target: String, targetCh: String): LetterStatus {
        return when (answerCh) {
            targetCh -> LetterStatus.CORRECT
            in target -> LetterStatus.EXIST
            else -> LetterStatus.INCORRECT
        }
    }

    fun resetAnswers(): SnapshotStateList<SnapshotStateList<String>> = mutableStateListOf(
        mutableStateListOf("", "", "", "", ""),
        mutableStateListOf("", "", "", "", ""),
        mutableStateListOf("", "", "", "", ""),
        mutableStateListOf("", "", "", "", ""),
        mutableStateListOf("", "", "", "", "")
    )

    fun resetStatuses() = mutableStateListOf(
        mutableStateListOf(
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT
        ),
        mutableStateListOf(
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT
        ),
        mutableStateListOf(
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT
        ),
        mutableStateListOf(
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT
        ),
        mutableStateListOf(
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT,
            LetterStatus.DEFAULT
        )
    )
}