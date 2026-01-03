package gas.control.project.domain.usecase

import gas.control.project.domain.repository.ConductoresRepository

class CreateConductorUseCase(
    private val conductoresRepository: ConductoresRepository
) {
    suspend operator fun invoke(
        nombre: String,
        apellido: String,
        email: String? = null,
        telefono: String? = null
    ): Result<Int> {
        return conductoresRepository.create(
            nombre = nombre,
            apellido = apellido,
            email = email,
            telefono = telefono
        )
    }
}
