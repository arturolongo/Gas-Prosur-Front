package gas.control.project.data.remote

import gas.control.project.data.storage.TokenStorage
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

/**
 * Función de extensión para agregar el token de autenticación a una request
 */
fun HttpRequestBuilder.addAuthToken(tokenStorage: TokenStorage?) {
    tokenStorage?.getToken()?.let { token ->
        header(HttpHeaders.Authorization, "Bearer $token")
    }
}

