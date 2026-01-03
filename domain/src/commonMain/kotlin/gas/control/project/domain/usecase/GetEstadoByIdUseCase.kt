package gas.control.project.domain.usecase

import gas.control.project.domain.model.Estado
import gas.control.project.domain.repository.CatalogosRepository

class GetEstadoByIdUseCase(
    private val catalogosRepository: CatalogosRepository
) {
    suspend operator fun invoke(id: Int): Result<Estado> {
        return catalogosRepository.getEstadoById(id)
    }
}

