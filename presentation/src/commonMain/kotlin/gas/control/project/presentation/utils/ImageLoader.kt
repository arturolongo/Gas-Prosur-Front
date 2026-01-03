package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Carga una imagen desde un URI string y la devuelve como ImageBitmap
 */
@Composable
expect fun rememberImageFromUri(uriString: String?): ImageBitmap?

