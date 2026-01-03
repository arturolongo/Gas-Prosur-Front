package gas.control.project.data.repository

import gas.control.project.data.remote.api.ViajesApiService
import gas.control.project.data.remote.dto.*
import gas.control.project.domain.model.PuntoRuta
import gas.control.project.domain.model.Viaje
import gas.control.project.domain.model.ViajeResumen
import gas.control.project.domain.repository.ViajesRepository

class ViajesRepositoryImpl(
    private val viajesApiService: ViajesApiService
) : ViajesRepository {
    
    override suspend fun getAll(
        idConductor: Int?,
        idAuto: Int?,
        idEstadoViaje: Int?,
        fechaInicio: String?,
        fechaFin: String?
    ): Result<List<ViajeResumen>> {
        return try {
            val response = viajesApiService.getAll(
                idConductor = idConductor,
                idAuto = idAuto,
                idEstadoViaje = idEstadoViaje,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin
            )
            val viajes = response.map { it.toDomain() }
            Result.success(viajes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getById(id: Int): Result<Viaje> {
        return try {
            val response = viajesApiService.getById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun ViajeResumenDto.toDomain(): ViajeResumen {
        return ViajeResumen(
            idViaje = idViaje,
            idConductor = idConductor,
            nombreConductor = nombreConductor,
            idAuto = idAuto,
            placaAuto = placaAuto,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            estadoViaje = estadoViaje
        )
    }
    
    private fun ViajeDetailDto.toDomain(): Viaje {
        return Viaje(
            idViaje = idViaje,
            idConductor = idConductor,
            nombreConductor = nombreConductor,
            idAuto = idAuto,
            placaAuto = placaAuto,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            estadoViaje = estadoViaje,
            puntosRuta = puntosRuta?.map { it.toDomain() }
        )
    }
    
    private fun PuntoRutaDto.toDomain(): PuntoRuta {
        return PuntoRuta(
            idPuntoRuta = idPuntoRuta,
            latitud = latitud,
            longitud = longitud,
            orden = orden,
            fechaHora = fechaHora
        )
    }
}
