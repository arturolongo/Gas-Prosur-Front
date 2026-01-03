package gas.control.project.domain.usecase

import gas.control.project.domain.model.Viaje
import gas.control.project.domain.repository.ViajesRepository

class GetViajeByIdUseCase(
    private val viajesRepository: ViajesRepository
) {
    suspend operator fun invoke(id: Int): Result<Viaje> {
        return viajesRepository.getById(id)
    }
}

