package gas.control.project.domain.model

data class Viaje(
    val idViaje: Int,
    val idConductor: Int,
    val nombreConductor: String,
    val idAuto: Int,
    val placaAuto: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val estadoViaje: String,
    val puntosRuta: List<PuntoRuta>? = null
)

data class ViajeResumen(
    val idViaje: Int,
    val idConductor: Int,
    val nombreConductor: String,
    val idAuto: Int,
    val placaAuto: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val estadoViaje: String
)

data class PuntoRuta(
    val idPuntoRuta: Int,
    val latitud: Double,
    val longitud: Double,
    val orden: Int,
    val fechaHora: String
)
