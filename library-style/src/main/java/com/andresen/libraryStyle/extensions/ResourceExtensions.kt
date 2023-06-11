package com.andresen.libraryStyle.extensions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberStringResource(
    @StringRes id: Int,
    argument: Any? = null
): String {
    val context = LocalContext.current
    return remember(id, context, argument) {
        if (argument != null) {
            context.getString(id, argument)
        } else {
            context.getString(id)
        }
    }
}

