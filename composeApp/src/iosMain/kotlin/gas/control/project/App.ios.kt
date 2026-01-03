package gas.control.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import gas.control.project.data.local.DatabaseDriverFactory
import gas.control.project.data.remote.HttpClientFactory

actual fun getHttpClientFactory(): HttpClientFactory {
    return HttpClientFactory()
}

@Composable
actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return remember { DatabaseDriverFactory() }
}

// Para iOS, usar localhost directamente
actual fun getBaseUrl(): String {
    return "http://localhost:5057/api"
}

