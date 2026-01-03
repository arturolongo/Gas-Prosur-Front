package gas.control.project.domain.usecase

import gas.control.project.domain.repository.AuthRepository

class IsAuthenticatedUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isAuthenticated()
    }
}

