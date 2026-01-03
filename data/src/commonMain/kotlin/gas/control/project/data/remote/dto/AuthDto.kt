package gas.control.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val roles: List<String>,
    val expiraEn: String
)

@Serializable
data class ApiError(
    val mensaje: String
)

