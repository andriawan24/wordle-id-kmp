package id.fawwaz.wordle.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.fawwaz.wordle.presentation.components.GameTitle
import id.fawwaz.wordle.presentation.components.Keyboard
import id.fawwaz.wordle.presentation.components.WordTile
import id.fawwaz.wordle.utils.LetterStatus
import id.fawwaz.wordle.viewmodels.GameViewModel
import id.fawwaz.wordle.viewmodels.models.GameEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen() {
    val gameViewModel: GameViewModel = koinViewModel()
    val state by gameViewModel.state.collectAsStateWithLifecycle()
    val tileStatuses by gameViewModel.letterStatuses.collectAsStateWithLifecycle()
    val values by gameViewModel.answers.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        gameViewModel.onEvent(GameEvent.OnStartGame)
    }

    GameContent(
        answers = values,
        statuses = tileStatuses,
        isError = state.isShaking,
        onCharClicked = { gameViewModel.onEvent(GameEvent.OnCharClicked(it)) },
        onDeleteClicked = { gameViewModel.onEvent(GameEvent.OnDeleteClicked) },
        onEnterClicked = { gameViewModel.onEvent(GameEvent.OnEnterClicked) },
        onErrorEnded = { gameViewModel.onEvent(GameEvent.OnErrorEnded) }
    )
}

@Composable
fun GameContent(
    isError: Boolean,
    onErrorEnded: () -> Unit,
    answers: SnapshotStateList<SnapshotStateList<String>>,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    statuses: SnapshotStateList<SnapshotStateList<LetterStatus>>
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        GameTitle(
            modifier = Modifier.padding(horizontal = 60.dp).padding(bottom = 24.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            answers.forEachIndexed { colIdx, col ->
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
                            letterStatus = statuses[colIdx][rowIdx],
                            isShaking = isError,
                            onShakingEnded = onErrorEnded
                        )
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Keyboard(
            onCharClicked = onCharClicked,
            onDeleteClicked = onDeleteClicked,
            onEnterClicked = onEnterClicked
        )
    }
}
