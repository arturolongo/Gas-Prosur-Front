package gas.control.project.presentation.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
actual fun rememberImageFromUri(uriString: String?): ImageBitmap? {
    val context = LocalContext.current
    var bitmap by remember(uriString) { mutableStateOf<ImageBitmap?>(null) }
    
    LaunchedEffect(uriString) {
        if (uriString == null) {
            bitmap = null
            return@LaunchedEffect
        }
        
        bitmap = withContext(Dispatchers.IO) {
            try {
                // Si es una URL HTTP/HTTPS, descargarla
                if (uriString.startsWith("http://") || uriString.startsWith("https://")) {
                    // Usar el HttpClient del módulo data a través de Koin
                    // Por ahora, usar una implementación simple con URLConnection
                    val url = java.net.URL(uriString)
                    val connection = url.openConnection()
                    connection.connect()
                    val inputStream = connection.getInputStream()
                    val androidBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()
                    androidBitmap?.asImageBitmap()
                } else {
                    // Si es un URI local, usar ImageUtils
                    val uri = Uri.parse(uriString)
                    val androidBitmap = ImageUtils.uriToBitmap(context as Any, uri as Any) as? android.graphics.Bitmap
                    androidBitmap?.asImageBitmap()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    
    return bitmap
}

