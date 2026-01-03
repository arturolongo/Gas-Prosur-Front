package gas.control.project.domain.usecase

import gas.control.project.domain.model.AuthUser
import gas.control.project.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser> {
        return authRepository.login(email, password)
    }
}

