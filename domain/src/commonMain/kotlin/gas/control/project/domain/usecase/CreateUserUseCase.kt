package gas.control.project.domain.usecase

import gas.control.project.domain.model.User
import gas.control.project.domain.repository.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> {
        // Validaciones básicas
        if (user.name.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }
        if (user.email.isBlank() || !user.email.contains("@")) {
            return Result.failure(IllegalArgumentException("El email no es válido"))
        }
        
        return userRepository.createUser(user)
    }
}

