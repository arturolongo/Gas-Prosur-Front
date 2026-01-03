package gas.control.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConductorDetailDto(
    val idConductor: Int,
    @SerialName("nombreUsuario")
    val nombre: String? = null,
    @SerialName("apellidoUsuario")
    val apellido: String? = null,
    val email: String? = null,
    val telefono: String? = null,
    @SerialName("idLicencia")
    val idLicencia: String? = null,
    @SerialName("tipoLicenciaCodigo")
    val tipoLicenciaCodigo: String? = null,
    @SerialName("estadoExpedicionNombre")
    val estadoExpedicionNombre: String? = null,
    @SerialName("fechaExpiracionLicencia")
    val fechaExpiracionLicencia: String? = null,
    @SerialName("licenciaVencida")
    val licenciaVencida: Boolean? = null,
    @SerialName("urlFotoLicencia")
    val urlFotoLicencia: String? = null,
    @SerialName("urlFotoPerfil")
    val urlFotoPerfil: String? = null,
    @SerialName("ultimoRecorridoDate")
    val ultimoRecorridoDate: String? = null,
    @SerialName("totalViajes")
    val totalViajes: Int? = null,
    @SerialName("totalAutosAsignados")
    val totalAutosAsignados: Int? = null,
    val licencias: List<LicenciaDto>? = null
)

@Serializable
data class LicenciaDto(
    val idLicencia: Int,
    val numeroLicencia: String,
    val tipoLicencia: String,
    val fechaVencimiento: String
)

@Serializable
data class CreateConductorDto(
    val nombre: String,
    val apellido: String,
    val email: String? = null,
    val telefono: String? = null
)

@Serializable
data class CompletarPerfilConductorDto(
    val nombreCompleto: String,
    val estadoExpedicion: String,
    val tipoLicencia: String,
    val anoExpiracion: String,
    val fotoLicenciaBase64: String? = null,  // Formato: "data:image/jpeg;base64,/9j/4AAQSkZJRg..."
    val fotoPerfilBase64: String? = null     // Formato: "data:image/jpeg;base64,/9j/4AAQSkZJRg..."
)

@Serializable
data class CompletarPerfilResponse(
    val id: Int,
    val mensaje: String
)

