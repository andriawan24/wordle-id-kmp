package id.fawwaz.wordle.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.fawwaz.wordle.presentation.components.GameDialog
import id.fawwaz.wordle.presentation.components.GameTitle
import id.fawwaz.wordle.presentation.components.Keyboard
import id.fawwaz.wordle.presentation.components.WordTile
import id.fawwaz.wordle.presentation.models.GameEvent
import id.fawwaz.wordle.theme.Dimension
import id.fawwaz.wordle.utils.enums.LetterStatus
import id.fawwaz.wordle.viewmodels.AnswersType
import id.fawwaz.wordle.viewmodels.GameViewModel
import indonesianwordle.composeapp.generated.resources.Res
import indonesianwordle.composeapp.generated.resources.action_play_again
import indonesianwordle.composeapp.generated.resources.action_try_again
import indonesianwordle.composeapp.generated.resources.label_message_revealed
import indonesianwordle.composeapp.generated.resources.message_failed
import indonesianwordle.composeapp.generated.resources.message_meaning
import indonesianwordle.composeapp.generated.resources.title_failed
import indonesianwordle.composeapp.generated.resources.title_failed_load_game
import indonesianwordle.composeapp.generated.resources.title_won
import org.jetbrains.compose.resources.stringResource
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

    GameDialog(
        isShowing = state.isWon,
        title = stringResource(Res.string.title_won),
        message = stringResource(Res.string.label_message_revealed, state.guessWord.id),
        description = stringResource(Res.string.message_meaning, state.guessWord.subMeaning),
        actionButton = {
            Button(onClick = { gameViewModel.onEvent(GameEvent.OnStartGame) }) {
                Text(stringResource(Res.string.action_play_again))
            }
        }
    )

    GameDialog(
        isShowing = state.message.isNotBlank(),
        title = stringResource(Res.string.title_failed),
        message = stringResource(Res.string.message_failed, state.message)
    )

    GameDialog(
        isShowing = state.isFailed,
        title = stringResource(Res.string.title_failed_load_game),
        message = stringResource(Res.string.message_failed, state.message),
        actionButton = {
            Button(onClick = { gameViewModel.onEvent(GameEvent.OnStartGame) }) {
                Text(stringResource(Res.string.action_try_again))
            }
        }
    )
}

@Composable
fun GameContent(
    isError: Boolean,
    onErrorEnded: () -> Unit,
    answers: AnswersType,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    statuses: SnapshotStateList<SnapshotStateList<LetterStatus>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = Dimension.SIZE_24)
            .windowInsetsPadding(WindowInsets.systemBars),
        verticalArrangement = Arrangement.spacedBy(Dimension.SIZE_24)
    ) {
        GameTitle(
            modifier = Modifier
                .padding(horizontal = Dimension.SIZE_60)
                .padding(bottom = Dimension.SIZE_24)
        )

        Column(verticalArrangement = Arrangement.spacedBy(Dimension.SIZE_4)) {
            answers.forEachIndexed { colIdx, col ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimension.SIZE_24),
                    horizontalArrangement = Arrangement.spacedBy(Dimension.SIZE_4)
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
