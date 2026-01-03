package gas.control.project.data.remote.api

import gas.control.project.data.remote.dto.UserDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

interface UserApiService {
    suspend fun getAllUsers(): List<UserDto>
    suspend fun getUserById(id: String): UserDto
    suspend fun createUser(user: UserDto): UserDto
    suspend fun updateUser(id: String, user: UserDto): UserDto
    suspend fun deleteUser(id: String): Unit
}

class UserApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : UserApiService {
    
    override suspend fun getAllUsers(): List<UserDto> {
        return httpClient.get("$baseUrl/users") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    
    override suspend fun getUserById(id: String): UserDto {
        return httpClient.get("$baseUrl/users/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    
    override suspend fun createUser(user: UserDto): UserDto {
        return httpClient.post("$baseUrl/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }
    
    override suspend fun updateUser(id: String, user: UserDto): UserDto {
        return httpClient.put("$baseUrl/users/$id") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }
    
    override suspend fun deleteUser(id: String): Unit {
        return httpClient.delete("$baseUrl/users/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}

