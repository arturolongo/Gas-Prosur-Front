package gas.control.project

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import gas.control.project.data.local.DatabaseDriverFactory
import gas.control.project.data.remote.HttpClientFactory

actual fun getHttpClientFactory(): HttpClientFactory {
    return HttpClientFactory()
}

@Composable
actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    val context = LocalContext.current
    return remember { DatabaseDriverFactory(context) }
}

// Para Android, usar la IP local de la computadora
actual fun getBaseUrl(): String {
    // Usar 192.168.110.227 para dispositivo físico
    // Cambiar a 10.0.2.2 si usas emulador
    return "http://192.168.110.227:5057/api"
}

