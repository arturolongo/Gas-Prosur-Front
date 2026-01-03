package gas.control.project.domain.usecase

import gas.control.project.domain.repository.ConductoresRepository

class UpdateConductorUseCase(
    private val conductoresRepository: ConductoresRepository
) {
    suspend operator fun invoke(
        id: Int,
        nombre: String,
        apellido: String,
        email: String? = null,
        telefono: String? = null
    ): Result<Unit> {
        return conductoresRepository.update(
            id = id,
            nombre = nombre,
            apellido = apellido,
            email = email,
            telefono = telefono
        )
    }
}
