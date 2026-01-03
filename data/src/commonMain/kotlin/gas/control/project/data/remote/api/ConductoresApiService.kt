package gas.control.project.data.remote.api

import gas.control.project.data.remote.addAuthToken
import gas.control.project.data.remote.dto.*
import gas.control.project.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface ConductoresApiService {
    suspend fun getAll(): List<ConductorDetailDto>
    suspend fun getById(id: Int): ConductorDetailDto? // Retorna null si no existe (404)
    suspend fun create(conductor: CreateConductorDto): CreateResponse
    suspend fun update(id: Int, conductor: CreateConductorDto): UpdateResponse
    suspend fun delete(id: Int): DeleteResponse
    suspend fun completarPerfil(idConductor: Int, perfilDto: CompletarPerfilConductorDto): CompletarPerfilResponse
}

class ConductoresApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
    private val tokenStorage: TokenStorage
) : ConductoresApiService {
    
    override suspend fun getAll(): List<ConductorDetailDto> {
        return httpClient.get("$baseUrl/conductores") {
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun getById(id: Int): ConductorDetailDto? {
        return try {
            println("🟡 [ConductoresApiService] GET $baseUrl/conductores/$id")
            val response: HttpResponse = httpClient.get("$baseUrl/conductores/$id") {
                addAuthToken(tokenStorage)
            }
            println("🟢 [ConductoresApiService] Respuesta recibida: ${response.status}")
            
            // Verificar el status code antes de parsear el body
            if (response.status == HttpStatusCode.NotFound) {
                println("🟡 [ConductoresApiService] 404 - Conductor no existe, retornando null")
                return null
            }
            
            if (response.status.value !in 200..299) {
                println("🔴 [ConductoresApiService] Status code no exitoso: ${response.status}")
                throw ClientRequestException(response, "Error ${response.status.value}")
            }
            
            println("🟡 [ConductoresApiService] Parseando body...")
            
            // Leer el body como string primero para debug
            val bodyBytes = response.readBytes()
            val bodyString = String(bodyBytes)
            println("🟡 [ConductoresApiService] Body raw (primeros 1000 chars): ${bodyString.take(1000)}")
            
            // Parsear desde el string
            val conductor = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            }.decodeFromString<ConductorDetailDto>(bodyString)
            
            println("🟢 [ConductoresApiService] Body parseado exitosamente: id=${conductor.idConductor}, nombre=${conductor.nombre}")
            conductor
        } catch (e: ClientRequestException) {
            println("🔴 [ConductoresApiService] ClientRequestException: ${e.response.status}")
            // Si es 404, el conductor no existe (perfil no completado)
            if (e.response.status == HttpStatusCode.NotFound) {
                println("🟡 [ConductoresApiService] 404 capturado - Conductor no existe, retornando null")
                null
            } else {
                // Para otros errores, relanzar la excepción
                println("🔴 [ConductoresApiService] Error diferente a 404, relanzando: ${e.response.status}")
                throw e
            }
        } catch (e: Exception) {
            println("🔴 [ConductoresApiService] Excepción al parsear: ${e.javaClass.simpleName} - ${e.message}")
            e.printStackTrace()
            
            // Si es un error de serialización, puede ser que el DTO no coincida
            if (e.message?.contains("serializ") == true || e.message?.contains("deserializ") == true) {
                println("🔴 [ConductoresApiService] ERROR DE SERIALIZACIÓN - El DTO puede no coincidir con la respuesta del backend")
            }
            
            // Intentar verificar si es un error HTTP 404
            val statusCode = when (e) {
                is ClientRequestException -> e.response.status
                else -> null
            }
            
            if (statusCode == HttpStatusCode.NotFound) {
                println("🟡 [ConductoresApiService] 404 detectado, retornando null")
                null
            } else {
                println("🔴 [ConductoresApiService] Error no es 404, relanzando excepción")
                throw e
            }
        }
    }
    
    override suspend fun create(conductor: CreateConductorDto): CreateResponse {
        return httpClient.post("$baseUrl/conductores") {
            setBody(conductor)
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun update(id: Int, conductor: CreateConductorDto): UpdateResponse {
        return httpClient.patch("$baseUrl/conductores/$id") {
            setBody(conductor)
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun delete(id: Int): DeleteResponse {
        return httpClient.delete("$baseUrl/conductores/$id") {
            addAuthToken(tokenStorage)
        }.body()
    }
    
    override suspend fun completarPerfil(
        idConductor: Int,
        perfilDto: CompletarPerfilConductorDto
    ): CompletarPerfilResponse {
        return try {
            println("🟡 [ConductoresApiService] Enviando POST a: $baseUrl/conductores/$idConductor/completar-perfil")
            println("🟡 [ConductoresApiService] DTO: nombreCompleto=${perfilDto.nombreCompleto}, estado=${perfilDto.estadoExpedicion}, tipo=${perfilDto.tipoLicencia}")
            
            val response = httpClient.post("$baseUrl/conductores/$idConductor/completar-perfil") {
                contentType(ContentType.Application.Json)
                setBody(perfilDto)
                addAuthToken(tokenStorage)
            }
            
            println("🟢 [ConductoresApiService] Respuesta recibida: ${response.status}")
            val body = response.body<CompletarPerfilResponse>()
            println("🟢 [ConductoresApiService] Body parseado: id=${body.id}, mensaje=${body.mensaje}")
            body
        } catch (e: Exception) {
            println("🔴 [ConductoresApiService] Error en completarPerfil: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
}
