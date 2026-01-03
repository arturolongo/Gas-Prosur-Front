package gas.control.project.domain.repository

import gas.control.project.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    /**
     * Obtiene todos los usuarios
     */
    fun getAllUsers(): Flow<List<User>>
    
    /**
     * Obtiene un usuario por su ID
     */
    suspend fun getUserById(id: String): User?
    
    /**
     * Obtiene un usuario por su email
     */
    suspend fun getUserByEmail(email: String): User?
    
    /**
     * Crea un nuevo usuario
     */
    suspend fun createUser(user: User): Result<User>
    
    /**
     * Actualiza un usuario existente
     */
    suspend fun updateUser(user: User): Result<User>
    
    /**
     * Elimina un usuario por su ID
     */
    suspend fun deleteUser(id: String): Result<Unit>
    
    /**
     * Sincroniza los usuarios desde la API
     */
    suspend fun syncUsers(): Result<Unit>
}

