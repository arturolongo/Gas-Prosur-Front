package gas.control.project.data.mapper

import gas.control.project.data.remote.dto.UserDto
import kotlinx.datetime.Instant

object UserMapper {
    
    /**
     * Convierte UserDto a User (entidad del dominio)
     */
    fun UserDto.toDomain(): gas.control.project.domain.model.User {
        return gas.control.project.domain.model.User(
            id = id,
            name = name,
            email = email,
            createdAt = createdAt?.toInstant(),
            updatedAt = updatedAt?.toInstant()
        )
    }
    
    /**
     * Convierte User (entidad del dominio) a UserDto
     */
    fun gas.control.project.domain.model.User.toDto(): UserDto {
        return UserDto(
            id = id,
            name = name,
            email = email,
            createdAt = createdAt?.toString(),
            updatedAt = updatedAt?.toString()
        )
    }
    
    /**
     * Convierte entidad de base de datos SQLDelight a User (entidad del dominio)
     */
    fun gas.control.project.data.local.User.toDomain(): gas.control.project.domain.model.User {
        return gas.control.project.domain.model.User(
            id = id,
            name = name,
            email = email,
            createdAt = created_at?.toInstant(),
            updatedAt = updated_at?.toInstant()
        )
    }
    
    /**
     * Extension function para convertir String a Instant
     */
    private fun String.toInstant(): Instant? {
        return try {
            Instant.parse(this)
        } catch (e: Exception) {
            null
        }
    }
}
