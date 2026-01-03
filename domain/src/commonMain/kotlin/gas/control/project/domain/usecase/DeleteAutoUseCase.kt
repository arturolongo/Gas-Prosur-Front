package gas.control.project.domain.usecase

import gas.control.project.domain.repository.AutosRepository

class DeleteAutoUseCase(
    private val autosRepository: AutosRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return autosRepository.delete(id)
    }
}

