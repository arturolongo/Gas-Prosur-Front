package gas.control.project.data.repository

import gas.control.project.data.remote.api.CatalogosApiService
import gas.control.project.data.remote.dto.EstadoDetailDto
import gas.control.project.domain.model.Estado
import gas.control.project.domain.repository.CatalogosRepository

class CatalogosRepositoryImpl(
    private val catalogosApiService: CatalogosApiService
) : CatalogosRepository {
    
    override suspend fun getEstados(): Result<List<Estado>> {
        return try {
            val response = catalogosApiService.getEstados()
            val estados = response.map { it.toDomain() }
            Result.success(estados)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getEstadoById(id: Int): Result<Estado> {
        return try {
            val response = catalogosApiService.getEstadoById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun EstadoDetailDto.toDomain(): Estado {
        return Estado(
            idEstado = idEstado,
            nombre = nombreEstado,
            descripcion = descripcion
        )
    }
}

