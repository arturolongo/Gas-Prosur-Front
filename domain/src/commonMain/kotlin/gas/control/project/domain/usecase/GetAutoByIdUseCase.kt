package gas.control.project.domain.usecase

import gas.control.project.domain.model.Auto
import gas.control.project.domain.repository.AutosRepository

class GetAutoByIdUseCase(
    private val autosRepository: AutosRepository
) {
    suspend operator fun invoke(id: Int): Result<Auto> {
        return autosRepository.getById(id)
    }
}

