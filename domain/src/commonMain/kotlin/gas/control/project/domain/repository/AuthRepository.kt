package gas.control.project.domain.repository

import gas.control.project.domain.model.AuthUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun logout()
    fun isAuthenticated(): Boolean
}

