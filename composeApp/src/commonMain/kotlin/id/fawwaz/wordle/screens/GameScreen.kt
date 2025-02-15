package id.fawwaz.wordle.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.fawwaz.wordle.components.GameTitle
import id.fawwaz.wordle.components.Keyboard
import id.fawwaz.wordle.components.WordTile
import id.fawwaz.wordle.utils.RevealType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen() {
    val scope = rememberCoroutineScope()

    var currentColumnIndex by remember { mutableStateOf(0) }
    var currentRowIndex by remember { mutableStateOf(0) }
    val guessWord by remember { mutableStateOf("LEMON") }
    val selectedValues = remember { mutableStateListOf<String>() }
    var values = remember {
        mutableStateListOf(
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", ""),
            mutableStateListOf("", "", "", "", "")
        )
    }
    var statuses = remember {
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
    }

    GameContent(
        guessWord = guessWord,
        values = values,
        selectedValues = selectedValues,
        statuses = statuses,
        onCharClicked = {
            val currentCols = values[currentColumnIndex]
            val currentRow = values[currentColumnIndex][currentRowIndex]

            currentCols[currentRowIndex] = if (currentRow.isBlank()) it else currentRow
            currentRowIndex = (currentRowIndex + 1).coerceAtMost(values.lastIndex)
        },
        onDeleteClicked = {
            val currentCols = values[currentColumnIndex]
            val currentRow = values[currentColumnIndex][currentRowIndex]

            if (currentRowIndex == values.lastIndex && currentRow.isNotBlank()) {
                currentCols[currentRowIndex] = ""
            } else {
                currentRowIndex = (currentRowIndex - 1).coerceAtLeast(0)
                currentCols[currentRowIndex] = ""
            }
        },
        onEnterClicked = {
            scope.launch {
                values[currentColumnIndex].forEachIndexed { index, answerCh ->
                    statuses[currentColumnIndex][index] = when (answerCh) {
                        guessWord[index].toString() -> RevealType.GREEN
                        in guessWord -> RevealType.YELLOW
                        else -> RevealType.GRAY
                    }
                    selectedValues.add(answerCh)
                    delay(1000)
                }
                currentColumnIndex = (currentColumnIndex + 1).coerceAtMost(values.lastIndex)
                currentRowIndex = 0
            }
        }
    )
}

@Composable
fun GameContent(
    guessWord: String,
    selectedValues: SnapshotStateList<String>,
    values: SnapshotStateList<SnapshotStateList<String>>,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    statuses: List<List<RevealType>>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GameTitle()

        Spacer(Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            values.forEachIndexed { colIdx, col ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    col.forEachIndexed { rowIdx, word ->
                        WordTile(
                            modifier = Modifier.weight(1f),
                            value = word,
                            reveal = statuses[colIdx][rowIdx]
                        )
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Keyboard(
            guessWord = guessWord,
            selectedValues = selectedValues,
            onCharClicked = onCharClicked,
            onDeleteClicked = onDeleteClicked,
            onEnterClicked = onEnterClicked
        )

        Spacer(Modifier.height(24.dp))
    }
}
