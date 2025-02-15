package id.fawwaz.wordle.utils

import android.app.Activity
import android.view.View

fun Activity.findRootView(): View {
    return window.decorView.findViewById(android.R.id.content)
}
