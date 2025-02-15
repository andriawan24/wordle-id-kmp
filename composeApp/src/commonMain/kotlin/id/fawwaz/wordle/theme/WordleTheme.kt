package id.fawwaz.wordle.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
fun WordleTheme(content: @Composable () -> Unit) {
    MaterialTheme(typography = MyTypography()) {
        content()
    }
}

object WordleTheme {
    val typography: Typography
        @Composable
        get() = MyTypography()
}
