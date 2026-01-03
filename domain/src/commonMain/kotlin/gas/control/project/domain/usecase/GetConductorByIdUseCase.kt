package gas.control.project.domain.usecase

import gas.control.project.domain.model.Conductor
import gas.control.project.domain.repository.ConductoresRepository

class GetConductorByIdUseCase(
    private val conductoresRepository: ConductoresRepository
) {
    suspend operator fun invoke(id: Int): Result<Conductor> {
        return conductoresRepository.getById(id)
    }
}

