package gas.control.project.data.remote

import gas.control.project.data.mapper.UserMapper.toDomain
import gas.control.project.data.mapper.UserMapper.toDto
import gas.control.project.data.remote.api.UserApiService
import gas.control.project.domain.model.User

interface UserRemoteDataSource {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: String): User
    suspend fun createUser(user: User): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: String): Unit
}

class UserRemoteDataSourceImpl(
    private val apiService: UserApiService
) : UserRemoteDataSource {
    
    override suspend fun getAllUsers(): List<User> {
        return apiService.getAllUsers().map { it.toDomain() }
    }
    
    override suspend fun getUserById(id: String): User {
        return apiService.getUserById(id).toDomain()
    }
    
    override suspend fun createUser(user: User): User {
        return apiService.createUser(user.toDto()).toDomain()
    }
    
    override suspend fun updateUser(user: User): User {
        return apiService.updateUser(user.id, user.toDto()).toDomain()
    }
    
    override suspend fun deleteUser(id: String): Unit {
        return apiService.deleteUser(id)
    }
}

