package gas.control.project.domain.usecase

import gas.control.project.domain.repository.AutosRepository

class CreateAutoUseCase(
    private val autosRepository: AutosRepository
) {
    suspend operator fun invoke(
        placa: String,
        idMarca: Int? = null,
        idModelo: Int? = null,
        año: Int? = null,
        color: String? = null,
        idTipoVehiculo: Int? = null
    ): Result<Int> {
        return autosRepository.create(
            placa = placa,
            idMarca = idMarca,
            idModelo = idModelo,
            año = año,
            color = color,
            idTipoVehiculo = idTipoVehiculo
        )
    }
}
