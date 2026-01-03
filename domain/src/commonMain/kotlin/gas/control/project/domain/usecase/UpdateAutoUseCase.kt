package gas.control.project.domain.usecase

import gas.control.project.domain.repository.AutosRepository

class UpdateAutoUseCase(
    private val autosRepository: AutosRepository
) {
    suspend operator fun invoke(
        id: Int,
        placa: String? = null,
        idMarca: Int? = null,
        idModelo: Int? = null,
        año: Int? = null,
        color: String? = null,
        idTipoVehiculo: Int? = null
    ): Result<Unit> {
        return autosRepository.update(
            id = id,
            placa = placa,
            idMarca = idMarca,
            idModelo = idModelo,
            año = año,
            color = color,
            idTipoVehiculo = idTipoVehiculo
        )
    }
}
