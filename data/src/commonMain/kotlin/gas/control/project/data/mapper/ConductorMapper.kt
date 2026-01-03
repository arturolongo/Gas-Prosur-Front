package gas.control.project.data.mapper

import gas.control.project.data.remote.dto.ConductorDetailDto
import gas.control.project.data.remote.dto.LicenciaDto
import gas.control.project.domain.model.Conductor
import gas.control.project.domain.model.Licencia

object ConductorMapper {
    
    /**
     * Convierte ConductorDetailDto a Conductor (entidad del dominio)
     */
    fun toDomain(dto: ConductorDetailDto): Conductor {
        return Conductor(
            idConductor = dto.idConductor,
            nombre = dto.nombre ?: "",
            apellido = dto.apellido ?: "",
            email = dto.email,
            telefono = dto.telefono,
            licencias = dto.licencias?.map { toDomain(it) }
        )
    }
    
    /**
     * Convierte LicenciaDto a Licencia (entidad del dominio)
     */
    fun toDomain(dto: LicenciaDto): Licencia {
        return Licencia(
            idLicencia = dto.idLicencia,
            numeroLicencia = dto.numeroLicencia,
            tipoLicencia = dto.tipoLicencia,
            fechaVencimiento = dto.fechaVencimiento
        )
    }
}

