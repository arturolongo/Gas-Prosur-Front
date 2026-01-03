package gas.control.project.domain.usecase

import gas.control.project.domain.repository.UserRepository

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("El ID no puede estar vacío"))
        }
        
        return userRepository.deleteUser(id)
    }
}

