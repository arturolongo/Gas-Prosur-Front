package gas.control.project.data.mapper

import gas.control.project.data.remote.dto.PuntoRutaDto
import gas.control.project.data.remote.dto.ViajeDetailDto
import gas.control.project.data.remote.dto.ViajeResumenDto
import gas.control.project.domain.model.PuntoRuta
import gas.control.project.domain.model.Viaje

object ViajeMapper {
    
    /**
     * Convierte ViajeResumenDto a Viaje (entidad del dominio)
     */
    fun toDomain(dto: ViajeResumenDto): Viaje {
        return Viaje(
            idViaje = dto.idViaje,
            idConductor = dto.idConductor,
            nombreConductor = dto.nombreConductor,
            idAuto = dto.idAuto,
            placaAuto = dto.placaAuto,
            fechaInicio = dto.fechaInicio,
            fechaFin = dto.fechaFin,
            estadoViaje = dto.estadoViaje,
            puntosRuta = null
        )
    }
    
    /**
     * Convierte ViajeDetailDto a Viaje (entidad del dominio)
     */
    fun toDomain(dto: ViajeDetailDto): Viaje {
        return Viaje(
            idViaje = dto.idViaje,
            idConductor = dto.idConductor,
            nombreConductor = dto.nombreConductor,
            idAuto = dto.idAuto,
            placaAuto = dto.placaAuto,
            fechaInicio = dto.fechaInicio,
            fechaFin = dto.fechaFin,
            estadoViaje = dto.estadoViaje,
            puntosRuta = dto.puntosRuta?.map { toDomain(it) }
        )
    }
    
    /**
     * Convierte PuntoRutaDto a PuntoRuta (entidad del dominio)
     */
    fun toDomain(dto: PuntoRutaDto): PuntoRuta {
        return PuntoRuta(
            idPuntoRuta = dto.idPuntoRuta,
            latitud = dto.latitud,
            longitud = dto.longitud,
            orden = dto.orden,
            fechaHora = dto.fechaHora
        )
    }
}

