package id.fawwaz.wordle.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import indonesianwordle.composeapp.generated.resources.Res
import indonesianwordle.composeapp.generated.resources.rethink_sans
import indonesianwordle.composeapp.generated.resources.rethink_sans_bold
import indonesianwordle.composeapp.generated.resources.rethink_sans_medium
import indonesianwordle.composeapp.generated.resources.rethink_sans_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun RethinkFontFamily() = FontFamily(
    Font(Res.font.rethink_sans, weight = FontWeight.ExtraLight),
    Font(Res.font.rethink_sans, weight = FontWeight.Light),
    Font(Res.font.rethink_sans, weight = FontWeight.Normal),
    Font(Res.font.rethink_sans_medium, weight = FontWeight.Medium),
    Font(Res.font.rethink_sans_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.rethink_sans_bold, weight = FontWeight.Bold),
    Font(Res.font.rethink_sans_bold, weight = FontWeight.ExtraBold),
)

@Composable
fun MyTypography() = Typography().run {
    val fontFamily = RethinkFontFamily()
    copy(
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily)
    )
}
