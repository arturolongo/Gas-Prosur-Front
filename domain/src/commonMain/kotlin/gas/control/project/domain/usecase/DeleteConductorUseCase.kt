package gas.control.project.domain.usecase

import gas.control.project.domain.repository.ConductoresRepository

class DeleteConductorUseCase(
    private val conductoresRepository: ConductoresRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return conductoresRepository.delete(id)
    }
}

