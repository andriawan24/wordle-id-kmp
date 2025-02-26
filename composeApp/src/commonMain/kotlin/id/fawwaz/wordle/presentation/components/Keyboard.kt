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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.correct
import id.fawwaz.wordle.theme.incorrect
import id.fawwaz.wordle.theme.keyboardDefault
import org.jetbrains.compose.ui.tooling.preview.Preview

private val Chars = listOf(
    listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
    listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
    listOf("ENTER", "Z", "X", "C", "V", "B", "N", "M", "DELETE")
)

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    guessWord: String,
    selectedValues: List<String>,
    onCharClicked: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onEnterClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Chars.forEach { values ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)) {
                values.forEach { char ->
                    when (char) {
                        "DELETE" -> KeyboardItemDelete(onDeleteClicked = onDeleteClicked)
                        "ENTER" -> KeyboardItemEnter(onEnterClicked = onEnterClicked)
                        else -> KeyboardItem(
                            char = char,
                            selectedValues = selectedValues,
                            onCharClicked = onCharClicked,
                            guessWord = guessWord
                        )
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
    guessWord: String,
    selectedValues: List<String>,
    onCharClicked: (char: String) -> Unit
) {
    val backgroundCard by animateColorAsState(
        targetValue = when {
            char in selectedValues && char in guessWord -> MaterialTheme.colorScheme.correct
            char in selectedValues && char !in guessWord -> MaterialTheme.colorScheme.incorrect
            else -> MaterialTheme.colorScheme.keyboardDefault
        },
        label = "Background Card Animated"
    )

    Box(
        modifier = modifier
            .width(30.dp)
            .clickable { onCharClicked(char) }
            .background(color = backgroundCard, shape = MaterialTheme.shapes.small)
            .padding(vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = char,
            style = WordleTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
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
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Enter",
            style = WordleTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
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
                color = Color(0xFFED8FB3),
                shape = MaterialTheme.shapes.small
            )
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp),
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = "Delete Button"
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
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
