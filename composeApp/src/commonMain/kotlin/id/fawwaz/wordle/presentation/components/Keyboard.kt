package id.fawwaz.wordle.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.fawwaz.wordle.theme.Dimension
import id.fawwaz.wordle.theme.TextSize
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.correct
import id.fawwaz.wordle.theme.exists
import id.fawwaz.wordle.theme.incorrect
import id.fawwaz.wordle.theme.keyboardDefault
import id.fawwaz.wordle.theme.keyboardRed
import id.fawwaz.wordle.utils.KeyboardHelper
import id.fawwaz.wordle.utils.emptyString
import id.fawwaz.wordle.utils.enums.LetterStatus
import id.fawwaz.wordle.viewmodels.GameViewModel
import indonesianwordle.composeapp.generated.resources.Res
import indonesianwordle.composeapp.generated.resources.label_delete_button
import indonesianwordle.composeapp.generated.resources.label_enter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    viewModel: GameViewModel = koinViewModel()
) {
    val keyboardStatuses by viewModel.keyboardStatuses.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.SIZE_4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyboardHelper.chars.forEach { values ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    Dimension.SIZE_4,
                    Alignment.CenterHorizontally
                )
            ) {
                values.forEach { char ->
                    when (char) {
                        KeyboardHelper.ENTER -> KeyboardItemEnter(onEnterClicked = onEnterClicked)
                        KeyboardHelper.DELETE -> KeyboardItemDelete(onDeleteClicked = onDeleteClicked)
                        else -> {
                            val backgroundCard by animateColorAsState(
                                targetValue = when (keyboardStatuses[char]) {
                                    LetterStatus.INCORRECT -> MaterialTheme.colorScheme.incorrect
                                    LetterStatus.EXIST -> MaterialTheme.colorScheme.exists
                                    LetterStatus.CORRECT -> MaterialTheme.colorScheme.correct
                                    else -> MaterialTheme.colorScheme.keyboardDefault
                                },
                                label = emptyString()
                            )

                            KeyboardItem(
                                char = char,
                                backgroundCardColor = backgroundCard,
                                onCharClicked = onCharClicked,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardItem(
    modifier: Modifier = Modifier,
    char: String,
    backgroundCardColor: Color,
    onCharClicked: (char: String) -> Unit
) {
    Box(
        modifier = modifier
            .width(Dimension.SIZE_32)
            .clickable { onCharClicked(char) }
            .background(color = backgroundCardColor, shape = MaterialTheme.shapes.small)
            .padding(vertical = Dimension.SIZE_8)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = char,
            style = WordleTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.SIZE_16
            )
        )
    }
}

@Composable
fun KeyboardItemEnter(modifier: Modifier = Modifier, onEnterClicked: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onEnterClicked() }
            .background(Color(0xFFED8FB3), shape = MaterialTheme.shapes.small)
            .padding(vertical = Dimension.SIZE_8, horizontal = Dimension.SIZE_12)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(Res.string.label_enter),
            style = WordleTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.SIZE_16
            )
        )
    }
}

@Composable
fun KeyboardItemDelete(modifier: Modifier = Modifier, onDeleteClicked: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onDeleteClicked() }
            .background(
                color = MaterialTheme.colorScheme.keyboardRed,
                shape = MaterialTheme.shapes.small
            )
            .padding(all = Dimension.SIZE_8)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(Dimension.SIZE_24),
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = stringResource(Res.string.label_delete_button)
        )
    }
}

@Preview
@Composable
private fun KeyboardPreview() {
    WordleTheme {
//        Keyboard(
//            selectedValues = mutableStateListOf("A"),
//            onCharClicked = { },
//            onEnterClicked = { },
//            onDeleteClicked = { },
//            guessWord = "LEMON"
//        )
    }
}

@Preview()
@Composable
private fun KeyboardItemPreview() {
    WordleTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(Dimension.SIZE_4)) {
            KeyboardItemEnter(onEnterClicked = { })
//            KeyboardItem(
//                char = "A",
//                onCharClicked = { },
//                selectedValues = mutableStateListOf("A"),
//                guessWord = "LEMON"
//            )
//            KeyboardItem(
//                char = "B",
//                onCharClicked = { },
//                selectedValues = mutableStateListOf("A"),
//                guessWord = "LEMON"
//            )
            KeyboardItemDelete(onDeleteClicked = { })
        }
    }
}
