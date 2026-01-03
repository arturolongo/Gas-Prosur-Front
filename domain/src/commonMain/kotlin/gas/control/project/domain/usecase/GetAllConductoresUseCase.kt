package gas.control.project.domain.usecase

import gas.control.project.domain.model.Conductor
import gas.control.project.domain.repository.ConductoresRepository

class GetAllConductoresUseCase(
    private val conductoresRepository: ConductoresRepository
) {
    suspend operator fun invoke(): Result<List<Conductor>> {
        return conductoresRepository.getAll()
    }
}

