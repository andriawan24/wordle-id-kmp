package id.fawwaz.wordle.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.utils.RevealType
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val Title = "WORDLE"

@Composable
fun GameTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 24.dp, horizontal = 60.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Title.forEach {
            WordTile(
                modifier = Modifier.weight(1f),
                value = it.toString(),
                reveal = RevealType.HIDDEN
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
