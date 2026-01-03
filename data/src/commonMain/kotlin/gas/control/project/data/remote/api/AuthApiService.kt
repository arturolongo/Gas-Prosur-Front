package gas.control.project.data.remote.api

import gas.control.project.data.remote.dto.LoginRequest
import gas.control.project.data.remote.dto.LoginResponse
import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface AuthApiService {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun logout()
    fun isAuthenticated(): Boolean
}

class AuthApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : AuthApiService {
    
    override suspend fun login(email: String, password: String): LoginResponse {
        return try {
            println("🟡 [AuthApiService] Intentando login a: $baseUrl/auth/login")
            val response = httpClient.post("$baseUrl/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }
            println("🟢 [AuthApiService] Respuesta recibida: ${response.status}")
            
            val loginResponse: LoginResponse = response.body()
            
            // Guardar token
            tokenStorage.saveToken(loginResponse.token)
            println("🟢 [AuthApiService] Login exitoso, token guardado")
            
            loginResponse
        } catch (e: Exception) {
            println("🔴 [AuthApiService] Error en login: ${e.javaClass.simpleName}")
            println("🔴 [AuthApiService] Mensaje: ${e.message}")
            println("🔴 [AuthApiService] URL intentada: $baseUrl/auth/login")
            e.printStackTrace()
            throw e
        }
    }
    
    override suspend fun logout() {
        tokenStorage.clearToken()
    }
    
    override fun isAuthenticated(): Boolean {
        return tokenStorage.isAuthenticated()
    }
}

