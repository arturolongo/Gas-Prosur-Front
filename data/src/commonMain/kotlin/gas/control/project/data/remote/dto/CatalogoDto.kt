package gas.control.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstadoDetailDto(
    val idEstado: Int,
    @SerialName("nombreEstado")
    val nombreEstado: String,
    val descripcion: String? = null
)

