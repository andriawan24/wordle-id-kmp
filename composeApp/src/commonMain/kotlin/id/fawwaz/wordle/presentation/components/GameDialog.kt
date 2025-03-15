package id.fawwaz.wordle.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.cardBackgroundNeutral
import id.fawwaz.wordle.utils.emptyString
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GameDialog(
    title: String,
    message: String,
    description: String = emptyString(),
    isShowing: Boolean,
    actionButton: (@Composable () -> Unit)? = null
) {
    if (isShowing) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.cardBackgroundNeutral,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(horizontal = 48.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = title,
                    style = WordleTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    style = WordleTheme.typography.bodyLarge
                )

                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        style = WordleTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                actionButton?.invoke()
            }
        }
    }
}

@Preview
@Composable
fun GameDialogPreview() {
    WordleTheme {
        GameDialog(
            title = "You failed!",
            message = "The word is: Testing",
            description = "It means blablablabalbalbalablabla",
            isShowing = true
        ) {
            Button(
                onClick = {

                }
            ) {
                Text("Try Again")
            }
        }
    }
}