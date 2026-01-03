package gas.control.project.data.remote.api

import gas.control.project.data.remote.addAuthToken
import gas.control.project.data.remote.dto.EstadoDetailDto
import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CatalogosApiService {
    suspend fun getEstados(): List<EstadoDetailDto>
    suspend fun getEstadoById(id: Int): EstadoDetailDto
}

class CatalogosApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : CatalogosApiService {
    
    override suspend fun getEstados(): List<EstadoDetailDto> {
        return httpClient.get("$baseUrl/catalogos/Estados") {
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun getEstadoById(id: Int): EstadoDetailDto {
        return httpClient.get("$baseUrl/catalogos/Estados/$id") {
            addAuthToken(tokenStorage)
        }.body()
    }
}

