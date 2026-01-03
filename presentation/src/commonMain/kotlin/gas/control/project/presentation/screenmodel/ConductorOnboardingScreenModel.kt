package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.repository.ConductoresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConductorOnboardingScreenModel(
    val nombre: String,
    private val idUsuario: Int,
    private val conductoresRepository: ConductoresRepository
) : ScreenModel {
    
    private val _licenciaFotoPath = MutableStateFlow<String?>(null)
    val licenciaFotoPath: StateFlow<String?> = _licenciaFotoPath.asStateFlow()
    
    private val _fotoPerfilPath = MutableStateFlow<String?>(null)
    val fotoPerfilPath: StateFlow<String?> = _fotoPerfilPath.asStateFlow()
    
    private val _nombreCompleto = MutableStateFlow("")
    val nombreCompleto: StateFlow<String> = _nombreCompleto.asStateFlow()
    
    private val _estadoExpedicion = MutableStateFlow("")
    val estadoExpedicion: StateFlow<String> = _estadoExpedicion.asStateFlow()
    
    private val _tipoLicencia = MutableStateFlow("")
    val tipoLicencia: StateFlow<String> = _tipoLicencia.asStateFlow()
    
    private val _anoExpiracion = MutableStateFlow("")
    val anoExpiracion: StateFlow<String> = _anoExpiracion.asStateFlow()
    
    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Idle)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()
    
    // Estado que indica si la sección de licencia está completa
    val licenciaCompleta: StateFlow<Boolean> = combine(
        _nombreCompleto,
        _estadoExpedicion,
        _tipoLicencia,
        _anoExpiracion,
        _licenciaFotoPath
    ) { nombre, estado, tipo, ano, foto ->
        nombre.isNotBlank() && estado.isNotBlank() && tipo.isNotBlank() && ano.isNotBlank() && foto != null
    }.stateIn(
        scope = screenModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = false
    )
    
    fun onLicenciaFotoTaken(photoPath: String?) {
        _licenciaFotoPath.value = photoPath
    }
    
    fun onFotoPerfilTaken(photoPath: String?) {
        _fotoPerfilPath.value = photoPath
    }
    
    fun updateNombreCompleto(value: String) {
        _nombreCompleto.value = value
    }
    
    fun updateEstadoExpedicion(value: String) {
        _estadoExpedicion.value = value
    }
    
    fun updateTipoLicencia(value: String) {
        _tipoLicencia.value = value
    }
    
    fun updateAnoExpiracion(value: String) {
        _anoExpiracion.value = value
    }
    
    fun onSubirLicenciaClick(onTakePhoto: () -> Unit) {
        onTakePhoto()
    }
    
    fun onEstablecerFotoPerfilClick(onTakePhoto: () -> Unit) {
        onTakePhoto()
    }
    
    fun onGuardar(
        fotoLicenciaBase64: String?,
        fotoPerfilBase64: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        screenModelScope.launch {
            _uiState.value = OnboardingUiState.Loading
            
            val nombreCompleto = _nombreCompleto.value
            val estadoExpedicion = _estadoExpedicion.value
            val tipoLicencia = _tipoLicencia.value
            val anoExpiracion = _anoExpiracion.value
            
            // Log para depuración
            println("🔵 [ConductorOnboarding] Iniciando guardado de perfil")
            println("🔵 [ConductorOnboarding] ID Usuario: $idUsuario")
            println("🔵 [ConductorOnboarding] Nombre: $nombreCompleto")
            println("🔵 [ConductorOnboarding] Estado: $estadoExpedicion")
            println("🔵 [ConductorOnboarding] Tipo: $tipoLicencia")
            println("🔵 [ConductorOnboarding] Año: $anoExpiracion")
            println("🔵 [ConductorOnboarding] Foto Licencia Base64: ${fotoLicenciaBase64?.take(50)}...")
            println("🔵 [ConductorOnboarding] Foto Perfil Base64: ${fotoPerfilBase64?.take(50)}...")
            
            // Validar campos requeridos
            if (nombreCompleto.isBlank() || estadoExpedicion.isBlank() || 
                tipoLicencia.isBlank() || anoExpiracion.isBlank()) {
                val errorMsg = "Por favor completa todos los campos"
                println("🔴 [ConductorOnboarding] Error de validación: $errorMsg")
                _uiState.value = OnboardingUiState.Error(errorMsg)
                onError(errorMsg)
                return@launch
            }
            
            try {
                println("🟡 [ConductorOnboarding] Llamando a completarPerfil...")
                conductoresRepository.completarPerfil(
                    idConductor = idUsuario,
                    nombreCompleto = nombreCompleto,
                    estadoExpedicion = estadoExpedicion,
                    tipoLicencia = tipoLicencia,
                    anoExpiracion = anoExpiracion,
                    fotoLicenciaBase64 = fotoLicenciaBase64,
                    fotoPerfilBase64 = fotoPerfilBase64
                ).fold(
                    onSuccess = {
                        println("🟢 [ConductorOnboarding] Perfil guardado exitosamente")
                        _uiState.value = OnboardingUiState.Success
                        onSuccess()
                    },
                    onFailure = { error ->
                        println("🔴 [ConductorOnboarding] Error al guardar: ${error.message}")
                        error.printStackTrace()
                        val errorMessage = error.message ?: "Error al guardar los datos"
                        _uiState.value = OnboardingUiState.Error(errorMessage)
                        onError(errorMessage)
                    }
                )
            } catch (e: Exception) {
                println("🔴 [ConductorOnboarding] Excepción no manejada: ${e.message}")
                e.printStackTrace()
                val errorMessage = e.message ?: "Error desconocido al guardar los datos"
                _uiState.value = OnboardingUiState.Error(errorMessage)
                onError(errorMessage)
            }
        }
    }
}

sealed class OnboardingUiState {
    object Idle : OnboardingUiState()
    object Loading : OnboardingUiState()
    object Success : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}

