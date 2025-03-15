package id.fawwaz.wordle.utils

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import id.fawwaz.wordle.utils.enums.LetterStatus

object KeyboardHelper {
    const val ENTER = "ENTER"
    const val DELETE = "DELETE"

    val chars = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf(ENTER, "Z", "X", "C", "V", "B", "N", "M", DELETE)
    )

    fun generateKeyboardStatuses(): SnapshotStateMap<String, LetterStatus> {
        val map = ('A'..'Z').map { it.toString() }.associateWith { LetterStatus.DEFAULT }.toMutableMap()
        val mutableStateMap = mutableStateMapOf<String, LetterStatus>()
        map.forEach { (key, value) -> mutableStateMap.put(key, value) }
        return mutableStateMap
    }
}