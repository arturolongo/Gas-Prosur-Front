package gas.control.project.domain.model

data class AuthUser(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val roles: List<String>,
    val expiraEn: String
)

