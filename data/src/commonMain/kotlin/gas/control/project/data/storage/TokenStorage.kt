package gas.control.project.data.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Almacenamiento del token JWT para autenticación
 */
class TokenStorage {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()
    
    fun saveToken(token: String) {
        _token.value = token
        // TODO: Guardar también en DataStore o SecureStorage según la plataforma
    }
    
    fun getToken(): String? = _token.value
    
    fun clearToken() {
        _token.value = null
        // TODO: Limpiar también de DataStore o SecureStorage
    }
    
    fun isAuthenticated(): Boolean = _token.value != null
}

