package id.fawwaz.wordle.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.cardBackgroundNeutral
import id.fawwaz.wordle.utils.RevealType
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WordTile(
    modifier: Modifier = Modifier,
    value: String,
    reveal: RevealType,
    textSize: TextUnit = 20.sp
) {
    val backgroundCard by animateColorAsState(
        targetValue = when (reveal) {
            RevealType.HIDDEN -> MaterialTheme.colorScheme.cardBackgroundNeutral
            RevealType.GRAY -> Color(0xFFF2EFE7)
            RevealType.YELLOW -> Color(0xFFEDCCBA)
            RevealType.GREEN -> Color(0xFFC0EEBD)
        },
        label = "Background Card Animated"
    )

    Box(
        modifier = modifier
            .background(
                color = backgroundCard,
                shape = MaterialTheme.shapes.small
            )
            .aspectRatio(1f)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = value,
            style = WordleTheme.typography.labelMedium.copy(
                fontSize = textSize,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview
@Composable
private fun WordTilePreview() {
    WordleTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                WordTile(modifier = Modifier.weight(1f), value = "W", reveal = RevealType.HIDDEN)
                WordTile(modifier = Modifier.weight(1f), value = "O", reveal = RevealType.GREEN)
                WordTile(modifier = Modifier.weight(1f), value = "R", reveal = RevealType.YELLOW)
                WordTile(modifier = Modifier.weight(1f), value = "D", reveal = RevealType.GRAY)
                WordTile(modifier = Modifier.weight(1f), value = "L", reveal = RevealType.GRAY)
                WordTile(modifier = Modifier.weight(1f), value = "E", reveal = RevealType.GRAY)
            }
        }
    }
}
