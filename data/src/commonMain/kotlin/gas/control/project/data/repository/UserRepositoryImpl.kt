package gas.control.project.data.repository

import gas.control.project.data.local.UserLocalDataSource
import gas.control.project.data.remote.UserRemoteDataSource
import gas.control.project.domain.model.User
import gas.control.project.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.datetime.Clock

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    
    override fun getAllUsers(): Flow<List<User>> {
        return localDataSource.getAllUsers()
            .catch { exception ->
                // En caso de error, intentar sincronizar
                syncUsers()
                throw exception
            }
    }
    
    override suspend fun getUserById(id: String): User? {
        return try {
            // Intentar obtener de la base de datos local primero
            localDataSource.getUserById(id) ?: run {
                // Si no existe localmente, obtener de la API y guardar
                val user = remoteDataSource.getUserById(id)
                localDataSource.insertUser(user)
                user
            }
        } catch (e: Exception) {
            // Si falla la API, intentar obtener de la base de datos local
            localDataSource.getUserById(id)
        }
    }
    
    override suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getUserByEmail(email)
    }
    
    override suspend fun createUser(user: User): Result<User> {
        return try {
            val createdUser = remoteDataSource.createUser(
                user.copy(
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                )
            )
            // Guardar en la base de datos local
            localDataSource.insertUser(createdUser)
            Result.success(createdUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val updatedUser = remoteDataSource.updateUser(
                user.copy(updatedAt = Clock.System.now())
            )
            // Actualizar en la base de datos local
            localDataSource.updateUser(updatedUser)
            Result.success(updatedUser)
        } catch (e: Exception) {
            // Si falla la API, intentar actualizar solo localmente
            try {
                localDataSource.updateUser(user.copy(updatedAt = Clock.System.now()))
                Result.success(user)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }
    
    override suspend fun deleteUser(id: String): Result<Unit> {
        return try {
            remoteDataSource.deleteUser(id)
            localDataSource.deleteUser(id)
            Result.success(Unit)
        } catch (e: Exception) {
            // Si falla la API, intentar eliminar solo localmente
            try {
                localDataSource.deleteUser(id)
                Result.success(Unit)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }
    
    override suspend fun syncUsers(): Result<Unit> {
        return try {
            // Obtener usuarios de la API
            val remoteUsers = remoteDataSource.getAllUsers()
            // Limpiar base de datos local
            localDataSource.deleteAllUsers()
            // Guardar usuarios remotos en la base de datos local
            localDataSource.insertUsers(remoteUsers)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

