package gas.control.project.domain.repository

import gas.control.project.domain.model.Estado

interface CatalogosRepository {
    suspend fun getEstados(): Result<List<Estado>>
    suspend fun getEstadoById(id: Int): Result<Estado>
}

