package gas.control.project.data.repository

import gas.control.project.data.remote.api.AuthApiService
import gas.control.project.data.remote.dto.LoginResponse
import gas.control.project.domain.model.AuthUser
import gas.control.project.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            val response = authApiService.login(email, password)
            val authUser = response.toDomain()
            Result.success(authUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun logout() {
        authApiService.logout()
    }
    
    override fun isAuthenticated(): Boolean {
        return authApiService.isAuthenticated()
    }
    
    private fun LoginResponse.toDomain(): AuthUser {
        return AuthUser(
            idUsuario = idUsuario,
            nombre = nombre,
            apellido = apellido,
            email = email,
            roles = roles,
            expiraEn = expiraEn
        )
    }
}

