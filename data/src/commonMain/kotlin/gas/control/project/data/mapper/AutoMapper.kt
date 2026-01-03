package gas.control.project.data.mapper

import gas.control.project.data.remote.dto.AutoDashboardDto
import gas.control.project.data.remote.dto.AutoDetailDto
import gas.control.project.domain.model.Auto

object AutoMapper {
    
    /**
     * Convierte AutoDashboardDto a Auto (entidad del dominio)
     */
    fun toDomain(dto: AutoDashboardDto): Auto {
        return Auto(
            idAuto = dto.idAuto,
            placa = dto.placa,
            marca = dto.marca,
            modelo = dto.modelo,
            año = null,
            color = null,
            estado = dto.estado,
            conductorAsignado = dto.conductorAsignado
        )
    }
    
    /**
     * Convierte AutoDetailDto a Auto (entidad del dominio)
     */
    fun toDomain(dto: AutoDetailDto): Auto {
        return Auto(
            idAuto = dto.idAuto,
            placa = dto.placa,
            marca = dto.marca,
            modelo = dto.modelo,
            año = dto.año,
            color = dto.color,
            estado = dto.estado,
            conductorAsignado = null
        )
    }
}

