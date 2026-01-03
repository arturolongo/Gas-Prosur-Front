package gas.control.project.domain.usecase

import gas.control.project.domain.model.ViajeResumen
import gas.control.project.domain.repository.ViajesRepository

class GetAllViajesUseCase(
    private val viajesRepository: ViajesRepository
) {
    suspend operator fun invoke(
        idConductor: Int? = null,
        idAuto: Int? = null,
        idEstadoViaje: Int? = null,
        fechaInicio: String? = null,
        fechaFin: String? = null
    ): Result<List<ViajeResumen>> {
        return viajesRepository.getAll(
            idConductor = idConductor,
            idAuto = idAuto,
            idEstadoViaje = idEstadoViaje,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin
        )
    }
}
