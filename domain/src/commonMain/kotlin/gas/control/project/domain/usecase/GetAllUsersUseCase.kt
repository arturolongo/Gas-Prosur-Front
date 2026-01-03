package gas.control.project.domain.usecase

import gas.control.project.domain.model.User
import gas.control.project.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return userRepository.getAllUsers()
    }
}

