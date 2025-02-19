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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fawwaz.wordle.components.GameTitle
import id.fawwaz.wordle.components.Keyboard
import id.fawwaz.wordle.components.WordTile
import id.fawwaz.wordle.utils.RevealType
import id.fawwaz.wordle.viewmodels.GameEvent
import id.fawwaz.wordle.viewmodels.GameViewModel

@Composable
fun GameScreen() {
    val gameViewModel: GameViewModel = viewModel { GameViewModel() }
    val state by gameViewModel.state.collectAsStateWithLifecycle()
    val tileStatuses by gameViewModel.statuses.collectAsStateWithLifecycle()
    val values by gameViewModel.values.collectAsStateWithLifecycle()

    GameContent(
        guessWord = state.guessWord,
        values = values,
        selectedValues = state.selectedValues,
        statuses = tileStatuses,
        isError = state.isError,
        onCharClicked = { gameViewModel.onEvent(GameEvent.OnCharClicked(it)) },
        onDeleteClicked = { gameViewModel.onEvent(GameEvent.OnDeleteClicked) },
        onEnterClicked = { gameViewModel.onEvent(GameEvent.OnEnterClicked) },
        onErrorEnded = { gameViewModel.onEvent(GameEvent.OnErrorEnded) }
    )
}

@Composable
fun GameContent(
    guessWord: String,
    isError: Boolean,
    onErrorEnded: () -> Unit,
    selectedValues: List<String>,
    values: SnapshotStateList<SnapshotStateList<String>>,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    statuses: SnapshotStateList<SnapshotStateList<RevealType>>
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
                            reveal = statuses[colIdx][rowIdx],
                            isError = isError,
                            isErrorEnded = onErrorEnded
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
