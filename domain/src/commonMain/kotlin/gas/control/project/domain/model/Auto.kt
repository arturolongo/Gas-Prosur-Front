package gas.control.project.domain.model

data class Auto(
    val idAuto: Int,
    val placa: String,
    val marca: String?,
    val modelo: String?,
    val año: Int?,
    val color: String?,
    val estado: String?,
    val conductorAsignado: String? = null
)

data class AutoDashboard(
    val idAuto: Int,
    val placa: String,
    val marca: String?,
    val modelo: String?,
    val estado: String?,
    val conductorAsignado: String?
)
