package gas.control.project.domain.usecase

import gas.control.project.domain.repository.UserRepository

class SyncUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.syncUsers()
    }
}

