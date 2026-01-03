package gas.control.project.domain.usecase

import gas.control.project.domain.model.User
import gas.control.project.domain.repository.UserRepository

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> {
        // Validaciones básicas
        if (user.id.isBlank()) {
            return Result.failure(IllegalArgumentException("El ID del usuario no puede estar vacío"))
        }
        if (user.name.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }
        if (user.email.isBlank() || !user.email.contains("@")) {
            return Result.failure(IllegalArgumentException("El email no es válido"))
        }
        
        return userRepository.updateUser(user)
    }
}

