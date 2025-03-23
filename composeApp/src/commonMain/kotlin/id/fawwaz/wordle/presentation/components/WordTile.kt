package id.fawwaz.wordle.presentation.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.cardBackgroundNeutral
import id.fawwaz.wordle.theme.correct
import id.fawwaz.wordle.theme.exists
import id.fawwaz.wordle.theme.incorrect
import id.fawwaz.wordle.utils.enums.LetterStatus
import indonesianwordle.composeapp.generated.resources.Res
import indonesianwordle.composeapp.generated.resources.label_background_card_animated
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SHAKE_TIMES = 3
private const val SHAKE_DURATION = 100

@Composable
fun WordTile(
    modifier: Modifier = Modifier,
    value: String,
    letterStatus: LetterStatus,
    textSize: TextUnit = 20.sp,
    isShaking: Boolean = false,
    onShakingEnded: () -> Unit
) {
    var shouldShake by remember { mutableStateOf(false) }

    val backgroundCard by animateColorAsState(
        targetValue = when (letterStatus) {
            LetterStatus.DEFAULT -> MaterialTheme.colorScheme.cardBackgroundNeutral
            LetterStatus.INCORRECT -> MaterialTheme.colorScheme.incorrect
            LetterStatus.EXIST -> MaterialTheme.colorScheme.exists
            LetterStatus.CORRECT -> MaterialTheme.colorScheme.correct
        },
        label = stringResource(Res.string.label_background_card_animated)
    )

    val offsetX by animateFloatAsState(
        targetValue = if (shouldShake) 10f else 0f,
        animationSpec = repeatable(
            iterations = SHAKE_TIMES, // Shake three times
            animation = tween(durationMillis = SHAKE_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = { shouldShake = false } // Reset shake after animation
    )

    LaunchedEffect(isShaking) {
        if (isShaking) {
            shouldShake = true
            onShakingEnded()
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
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.DEFAULT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "O",
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.CORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "R",
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.EXIST
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "D",
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.INCORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "L",
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.INCORRECT
                )
                WordTile(
                    modifier = Modifier.weight(1f),
                    value = "E",
                    isShaking = false,
                    onShakingEnded = { },
                    letterStatus = LetterStatus.INCORRECT
                )
            }
        }
    }
}
