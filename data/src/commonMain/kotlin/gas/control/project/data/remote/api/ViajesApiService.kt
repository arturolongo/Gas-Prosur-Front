package gas.control.project.data.remote.api

import gas.control.project.data.remote.addAuthToken
import gas.control.project.data.remote.dto.ViajeDetailDto
import gas.control.project.data.remote.dto.ViajeResumenDto
import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*

interface ViajesApiService {
    suspend fun getAll(
        idConductor: Int? = null,
        idAuto: Int? = null,
        idEstadoViaje: Int? = null,
        fechaInicio: String? = null,
        fechaFin: String? = null
    ): List<ViajeResumenDto>
    suspend fun getById(id: Int): ViajeDetailDto
}

class ViajesApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : ViajesApiService {
    
    override suspend fun getAll(
        idConductor: Int?,
        idAuto: Int?,
        idEstadoViaje: Int?,
        fechaInicio: String?,
        fechaFin: String?
    ): List<ViajeResumenDto> {
        return httpClient.get("$baseUrl/viajes") {
            idConductor?.let { parameter("idConductor", it) }
            idAuto?.let { parameter("idAuto", it) }
            idEstadoViaje?.let { parameter("idEstadoViaje", it) }
            fechaInicio?.let { parameter("fechaInicio", it) }
            fechaFin?.let { parameter("fechaFin", it) }
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun getById(id: Int): ViajeDetailDto {
        return httpClient.get("$baseUrl/viajes/$id") {
            addAuthToken(tokenStorage)
        }.body()
    }
}
