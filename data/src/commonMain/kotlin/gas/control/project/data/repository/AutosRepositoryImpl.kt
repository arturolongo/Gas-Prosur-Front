package gas.control.project.data.repository

import gas.control.project.data.remote.api.AutosApiService
import gas.control.project.data.remote.dto.*
import gas.control.project.domain.model.Auto
import gas.control.project.domain.model.AutoDashboard
import gas.control.project.domain.repository.AutosRepository

class AutosRepositoryImpl(
    private val autosApiService: AutosApiService
) : AutosRepository {
    
    override suspend fun getDashboard(): Result<List<AutoDashboard>> {
        return try {
            val response = autosApiService.getDashboard()
            val autos = response.map { it.toDomain() }
            Result.success(autos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getById(id: Int): Result<Auto> {
        return try {
            val response = autosApiService.getById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun create(
        placa: String,
        idMarca: Int?,
        idModelo: Int?,
        año: Int?,
        color: String?,
        idTipoVehiculo: Int?
    ): Result<Int> {
        return try {
            val dto = CreateAutoDto(
                placa = placa,
                idMarca = idMarca,
                idModelo = idModelo,
                año = año,
                color = color,
                idTipoVehiculo = idTipoVehiculo
            )
            val response = autosApiService.create(dto)
            Result.success(response.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun update(
        id: Int,
        placa: String?,
        idMarca: Int?,
        idModelo: Int?,
        año: Int?,
        color: String?,
        idTipoVehiculo: Int?
    ): Result<Unit> {
        return try {
            val dto = UpdateAutoDto(
                placa = placa,
                idMarca = idMarca,
                idModelo = idModelo,
                año = año,
                color = color,
                idTipoVehiculo = idTipoVehiculo
            )
            autosApiService.update(id, dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun delete(id: Int): Result<Unit> {
        return try {
            autosApiService.delete(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun AutoDashboardDto.toDomain(): AutoDashboard {
        return AutoDashboard(
            idAuto = idAuto,
            placa = placa,
            marca = marca,
            modelo = modelo,
            estado = estado,
            conductorAsignado = conductorAsignado
        )
    }
    
    private fun AutoDetailDto.toDomain(): Auto {
        return Auto(
            idAuto = idAuto,
            placa = placa,
            marca = marca,
            modelo = modelo,
            año = año,
            color = color,
            estado = estado
        )
    }
}
