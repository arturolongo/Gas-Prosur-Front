package gas.control.project.data.remote

import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class HttpClientFactory {
    actual fun create(tokenStorage: TokenStorage?, baseUrl: String): HttpClient {
        return HttpClient(Darwin) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}

