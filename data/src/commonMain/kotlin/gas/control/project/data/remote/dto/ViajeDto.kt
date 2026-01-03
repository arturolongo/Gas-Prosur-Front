package gas.control.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ViajeResumenDto(
    val idViaje: Int,
    val idConductor: Int,
    val nombreConductor: String,
    val idAuto: Int,
    val placaAuto: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val estadoViaje: String
)

@Serializable
data class ViajeDetailDto(
    val idViaje: Int,
    val idConductor: Int,
    val nombreConductor: String,
    val idAuto: Int,
    val placaAuto: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val estadoViaje: String,
    val puntosRuta: List<PuntoRutaDto>? = null
)

@Serializable
data class PuntoRutaDto(
    val idPuntoRuta: Int,
    val latitud: Double,
    val longitud: Double,
    val orden: Int,
    val fechaHora: String
)

