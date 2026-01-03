package gas.control.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AutoDashboardDto(
    val idAuto: Int,
    val placa: String,
    val marca: String?,
    val modelo: String?,
    val estado: String?,
    val conductorAsignado: String?
)

@Serializable
data class AutoDetailDto(
    val idAuto: Int,
    val placa: String,
    val marca: String?,
    val modelo: String?,
    val año: Int?,
    val color: String?,
    val estado: String?
)

@Serializable
data class CreateAutoDto(
    val placa: String,
    val idMarca: Int? = null,
    val idModelo: Int? = null,
    val año: Int? = null,
    val color: String? = null,
    val idTipoVehiculo: Int? = null
)

@Serializable
data class UpdateAutoDto(
    val placa: String? = null,
    val idMarca: Int? = null,
    val idModelo: Int? = null,
    val año: Int? = null,
    val color: String? = null,
    val idTipoVehiculo: Int? = null
)

@Serializable
data class CreateResponse(
    val id: Int,
    val mensaje: String
)

@Serializable
data class UpdateResponse(
    val id: Int,
    val mensaje: String
)

@Serializable
data class DeleteResponse(
    val id: Int,
    val mensaje: String
)

