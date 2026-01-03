package gas.control.project.domain.usecase

import gas.control.project.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}

