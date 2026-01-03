package gas.control.project.data.remote

import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(tokenStorage: TokenStorage? = null, baseUrl: String = "http://localhost:5057/api"): HttpClient
}

