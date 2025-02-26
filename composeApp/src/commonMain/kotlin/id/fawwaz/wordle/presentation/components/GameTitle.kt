package id.fawwaz.wordle.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.utils.LetterStatus
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val Title = "WORDLE"

@Composable
fun GameTitle(modifier: Modifier = Modifier) {
    val title by remember { mutableStateOf(Title) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        title.forEach {
            WordTile(
                modifier = Modifier.weight(1f),
                value = it.toString(),
                letterStatus = LetterStatus.DEFAULT,
                isShaking = false,
                onShakingEnded = {}
            )
        }
    }
}

@Preview
@Composable
private fun GameTitlePreview() {
    WordleTheme {
        GameTitle()
    }
}
