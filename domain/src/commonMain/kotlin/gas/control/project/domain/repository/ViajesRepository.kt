package gas.control.project.domain.repository

import gas.control.project.domain.model.Viaje
import gas.control.project.domain.model.ViajeResumen

interface ViajesRepository {
    suspend fun getAll(
        idConductor: Int? = null,
        idAuto: Int? = null,
        idEstadoViaje: Int? = null,
        fechaInicio: String? = null,
        fechaFin: String? = null
    ): Result<List<ViajeResumen>>
    suspend fun getById(id: Int): Result<Viaje>
}
