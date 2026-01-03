package gas.control.project.domain.model

data class Conductor(
    val idConductor: Int,
    val nombre: String,
    val apellido: String,
    val email: String?,
    val telefono: String?,
    val idLicencia: String? = null,
    val tipoLicenciaCodigo: String? = null,
    val estadoExpedicionNombre: String? = null,
    val fechaExpiracionLicencia: String? = null,
    val licenciaVencida: Boolean? = null,
    val urlFotoLicencia: String? = null,
    val urlFotoPerfil: String? = null,
    val ultimoRecorridoDate: String? = null,
    val totalViajes: Int? = null,
    val totalAutosAsignados: Int? = null,
    val licencias: List<Licencia>? = null
)

data class Licencia(
    val idLicencia: Int,
    val numeroLicencia: String,
    val tipoLicencia: String,
    val fechaVencimiento: String
)

