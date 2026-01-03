package gas.control.project.domain.repository

import gas.control.project.domain.model.Auto
import gas.control.project.domain.model.AutoDashboard

interface AutosRepository {
    suspend fun getDashboard(): Result<List<AutoDashboard>>
    suspend fun getById(id: Int): Result<Auto>
    suspend fun create(
        placa: String,
        idMarca: Int? = null,
        idModelo: Int? = null,
        año: Int? = null,
        color: String? = null,
        idTipoVehiculo: Int? = null
    ): Result<Int>
    suspend fun update(
        id: Int,
        placa: String? = null,
        idMarca: Int? = null,
        idModelo: Int? = null,
        año: Int? = null,
        color: String? = null,
        idTipoVehiculo: Int? = null
    ): Result<Unit>
    suspend fun delete(id: Int): Result<Unit>
}
