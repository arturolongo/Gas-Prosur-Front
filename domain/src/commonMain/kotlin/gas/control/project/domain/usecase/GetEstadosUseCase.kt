package gas.control.project.domain.usecase

import gas.control.project.domain.model.Estado
import gas.control.project.domain.repository.CatalogosRepository

class GetEstadosUseCase(
    private val catalogosRepository: CatalogosRepository
) {
    suspend operator fun invoke(): Result<List<Estado>> {
        return catalogosRepository.getEstados()
    }
}

