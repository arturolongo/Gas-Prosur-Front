package gas.control.project.data.remote.api

import gas.control.project.data.remote.addAuthToken
import gas.control.project.data.remote.dto.*
import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*

interface AutosApiService {
    suspend fun getDashboard(): List<AutoDashboardDto>
    suspend fun getById(id: Int): AutoDetailDto
    suspend fun create(auto: CreateAutoDto): CreateResponse
    suspend fun update(id: Int, auto: UpdateAutoDto): UpdateResponse
    suspend fun delete(id: Int): DeleteResponse
}

class AutosApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : AutosApiService {
    
    override suspend fun getDashboard(): List<AutoDashboardDto> {
        return httpClient.get("$baseUrl/autos/dashboard") {
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun getById(id: Int): AutoDetailDto {
        return httpClient.get("$baseUrl/autos/$id") {
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun create(auto: CreateAutoDto): CreateResponse {
        return httpClient.post("$baseUrl/autos") {
            setBody(auto)
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun update(id: Int, auto: UpdateAutoDto): UpdateResponse {
        return httpClient.patch("$baseUrl/autos/$id") {
            setBody(auto)
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun delete(id: Int): DeleteResponse {
        return httpClient.delete("$baseUrl/autos/$id") {
            addAuthToken(tokenStorage)
        }.body()
    }
}
