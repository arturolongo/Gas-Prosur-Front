package gas.control.project.domain.usecase

import gas.control.project.domain.model.User
import gas.control.project.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String): User? {
        return userRepository.getUserById(id)
    }
}

