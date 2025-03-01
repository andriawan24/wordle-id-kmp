package id.fawwaz.wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import id.fawwaz.wordle.utils.findRootView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide system UI
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, findRootView()).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }

        setContent {
            App()
        }
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun AppAndroidPreview() {
    App()
}
