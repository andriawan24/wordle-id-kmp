package id.fawwaz.wordle.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.cardBackgroundNeutral
import id.fawwaz.wordle.utils.LetterStatus
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WordTile(
    modifier: Modifier = Modifier,
    value: String,
    reveal: LetterStatus,
    textSize: TextUnit = 20.sp,
    isError: Boolean = false,
    isErrorEnded: () -> Unit
) {
    var shouldShake by remember { mutableStateOf(false) }

    val backgroundCard by animateColorAsState(
        targetValue = when (reveal) {
            LetterStatus.DEFAULT -> MaterialTheme.colorScheme.cardBackgroundNeutral
            LetterStatus.INCORRECT -> Color(0xFFF2EFE7)
            LetterStatus.EXIST -> Color(0xFFEDCCBA)
            LetterStatus.CORRECT -> Color(0xFFC0EEBD)
        },
        label = "Background Card Animated"
    )

    val offsetX by animateFloatAsState(
        targetValue = if (shouldShake) 10f else 0f,
        animationSpec = repeatable(
            iterations = 3, // Shake three times
            animation = tween(durationMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = { shouldShake = false } // Reset shake after animation
    )

    LaunchedEffect(isError) {
        if (isError) {
            shouldShake = true
            isErrorEnded()
        }
    }

    Box(
        modifier = modifier
            .offset(x = offsetX.dp)
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
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "W",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.DEFAULT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "O",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.CORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "R",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.EXIST
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "D",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.INCORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "L",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.INCORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "E",
                    isError = false,
                    isErrorEnded = { },
                    reveal = LetterStatus.INCORRECT
                )
            }
        }
    }
}
