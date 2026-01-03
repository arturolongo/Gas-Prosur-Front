package gas.control.project.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import gas.control.project.data.mapper.UserMapper
import gas.control.project.data.mapper.UserMapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import gas.control.project.data.local.User as DbUser

interface UserLocalDataSource {
    fun getAllUsers(): Flow<List<gas.control.project.domain.model.User>>
    suspend fun getUserById(id: String): gas.control.project.domain.model.User?
    suspend fun getUserByEmail(email: String): gas.control.project.domain.model.User?
    suspend fun insertUser(user: gas.control.project.domain.model.User)
    suspend fun insertUsers(users: List<gas.control.project.domain.model.User>)
    suspend fun updateUser(user: gas.control.project.domain.model.User)
    suspend fun deleteUser(id: String)
    suspend fun deleteAllUsers()
}

class UserLocalDataSourceImpl(
    private val database: GasControlDatabase
) : UserLocalDataSource {
    
    override fun getAllUsers(): Flow<List<gas.control.project.domain.model.User>> {
        return database.userQueries
            .getAllUsers()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { users ->
                users.map { it.toDomain() }
            }
    }
    
    override suspend fun getUserById(id: String): gas.control.project.domain.model.User? {
        return database.userQueries
            .getUserById(id)
            .executeAsOneOrNull()
            ?.toDomain()
    }
    
    override suspend fun getUserByEmail(email: String): gas.control.project.domain.model.User? {
        return database.userQueries
            .getUserByEmail(email)
            .executeAsOneOrNull()
            ?.toDomain()
    }
    
    override suspend fun insertUser(user: gas.control.project.domain.model.User) {
        database.userQueries.insertUser(
            id = user.id,
            name = user.name,
            email = user.email,
            created_at = user.createdAt?.toString(),
            updated_at = user.updatedAt?.toString()
        )
    }
    
    override suspend fun insertUsers(users: List<gas.control.project.domain.model.User>) {
        database.transaction {
            users.forEach { user ->
                database.userQueries.insertUser(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    created_at = user.createdAt?.toString(),
                    updated_at = user.updatedAt?.toString()
                )
            }
        }
    }
    
    override suspend fun updateUser(user: gas.control.project.domain.model.User) {
        database.userQueries.updateUser(
            name = user.name,
            email = user.email,
            updated_at = user.updatedAt?.toString(),
            id = user.id
        )
    }
    
    override suspend fun deleteUser(id: String) {
        database.userQueries.deleteUser(id)
    }
    
    override suspend fun deleteAllUsers() {
        database.userQueries.deleteAllUsers()
    }
}

