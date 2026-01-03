package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun rememberImageFromUri(uriString: String?): ImageBitmap? {
    // TODO: Implementar carga de imagen desde URI en iOS
    return remember(uriString) {
        null
    }
}

