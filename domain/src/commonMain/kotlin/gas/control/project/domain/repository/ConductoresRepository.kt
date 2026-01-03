package gas.control.project.domain.repository

import gas.control.project.domain.model.Conductor

interface ConductoresRepository {
    suspend fun getAll(): Result<List<Conductor>>
    suspend fun getById(id: Int): Result<Conductor>
    suspend fun create(
        nombre: String,
        apellido: String,
        email: String? = null,
        telefono: String? = null
    ): Result<Int>
    suspend fun update(
        id: Int,
        nombre: String,
        apellido: String,
        email: String? = null,
        telefono: String? = null
    ): Result<Unit>
    suspend fun delete(id: Int): Result<Unit>
    suspend fun completarPerfil(
        idConductor: Int,
        nombreCompleto: String,
        estadoExpedicion: String,
        tipoLicencia: String,
        anoExpiracion: String,
        fotoLicenciaBase64: String? = null,
        fotoPerfilBase64: String? = null
    ): Result<Unit>
}
