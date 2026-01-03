package gas.control.project.data.repository

import gas.control.project.data.remote.api.ConductoresApiService
import gas.control.project.data.remote.dto.*
import gas.control.project.domain.model.Conductor
import gas.control.project.domain.model.Licencia
import gas.control.project.domain.repository.ConductoresRepository

class ConductoresRepositoryImpl(
    private val conductoresApiService: ConductoresApiService
) : ConductoresRepository {
    
    override suspend fun getAll(): Result<List<Conductor>> {
        return try {
            val response = conductoresApiService.getAll()
            val conductores = response.map { it.toDomain() }
            Result.success(conductores)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getById(id: Int): Result<Conductor> {
        return try {
            println("🟡 [ConductoresRepository] Llamando a getById para id: $id")
            val response = conductoresApiService.getById(id)
            if (response == null) {
                // Conductor no existe (404) = perfil no completado
                println("🟡 [ConductoresRepository] Respuesta null - Conductor no encontrado (perfil no completado)")
                Result.failure(Exception("Conductor no encontrado (perfil no completado)"))
            } else {
                println("🟢 [ConductoresRepository] Conductor encontrado: ${response.nombre} ${response.apellido}")
                Result.success(response.toDomain())
            }
        } catch (e: Exception) {
            println("🔴 [ConductoresRepository] Error en getById: ${e.javaClass.simpleName} - ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
    
    override suspend fun create(
        nombre: String,
        apellido: String,
        email: String?,
        telefono: String?
    ): Result<Int> {
        return try {
            val dto = CreateConductorDto(
                nombre = nombre,
                apellido = apellido,
                email = email,
                telefono = telefono
            )
            val response = conductoresApiService.create(dto)
            Result.success(response.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun update(
        id: Int,
        nombre: String,
        apellido: String,
        email: String?,
        telefono: String?
    ): Result<Unit> {
        return try {
            val dto = CreateConductorDto(
                nombre = nombre,
                apellido = apellido,
                email = email,
                telefono = telefono
            )
            conductoresApiService.update(id, dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun delete(id: Int): Result<Unit> {
        return try {
            conductoresApiService.delete(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun completarPerfil(
        idConductor: Int,
        nombreCompleto: String,
        estadoExpedicion: String,
        tipoLicencia: String,
        anoExpiracion: String,
        fotoLicenciaBase64: String?,
        fotoPerfilBase64: String?
    ): Result<Unit> {
        return try {
            println("🟡 [ConductoresRepository] Iniciando completarPerfil para idConductor: $idConductor")
            val dto = CompletarPerfilConductorDto(
                nombreCompleto = nombreCompleto,
                estadoExpedicion = estadoExpedicion,
                tipoLicencia = tipoLicencia,
                anoExpiracion = anoExpiracion,
                fotoLicenciaBase64 = fotoLicenciaBase64,
                fotoPerfilBase64 = fotoPerfilBase64
            )
            println("🟡 [ConductoresRepository] Llamando a conductoresApiService.completarPerfil...")
            val response = conductoresApiService.completarPerfil(idConductor, dto)
            println("🟢 [ConductoresRepository] Respuesta recibida: id=${response.id}, mensaje=${response.mensaje}")
            Result.success(Unit)
        } catch (e: Exception) {
            println("🔴 [ConductoresRepository] Error en completarPerfil: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
    
    private fun ConductorDetailDto.toDomain(): Conductor {
        return Conductor(
            idConductor = idConductor,
            nombre = nombre ?: "",
            apellido = apellido ?: "",
            email = email,
            telefono = telefono,
            idLicencia = idLicencia,
            tipoLicenciaCodigo = tipoLicenciaCodigo,
            estadoExpedicionNombre = estadoExpedicionNombre,
            fechaExpiracionLicencia = fechaExpiracionLicencia,
            licenciaVencida = licenciaVencida,
            urlFotoLicencia = urlFotoLicencia,
            urlFotoPerfil = urlFotoPerfil,
            ultimoRecorridoDate = ultimoRecorridoDate,
            totalViajes = totalViajes,
            totalAutosAsignados = totalAutosAsignados,
            licencias = licencias?.map { it.toDomain() }
        )
    }
    
    private fun LicenciaDto.toDomain(): Licencia {
        return Licencia(
            idLicencia = idLicencia,
            numeroLicencia = numeroLicencia,
            tipoLicencia = tipoLicencia,
            fechaVencimiento = fechaVencimiento
        )
    }
}
