package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.model.AuthUser
import gas.control.project.domain.usecase.GetConductorByIdUseCase
import gas.control.project.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val loginUseCase: LoginUseCase,
    private val getConductorByIdUseCase: GetConductorByIdUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario.asStateFlow()
    
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()
    
    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()
    
    private val _authUser = MutableStateFlow<AuthUser?>(null)
    val authUser: StateFlow<AuthUser?> = _authUser.asStateFlow()
    
    fun updateUsuario(value: String) {
        _usuario.value = value
    }
    
    fun updatePassword(value: String) {
        _password.value = value
    }
    
    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }
    
    fun login(
        onSuccess: (AuthUser) -> Unit,
        onProfileComplete: (AuthUser) -> Unit,
        onProfileIncomplete: (AuthUser) -> Unit
    ) {
        screenModelScope.launch {
            _uiState.value = LoginUiState.Loading
            loginUseCase(_usuario.value, _password.value).fold(
                onSuccess = { authUser ->
                    _authUser.value = authUser
                    _uiState.value = LoginUiState.Success
                    
                    // Verificar si el perfil está completo (solo para conductores)
                    if (authUser.roles.contains("Conductor")) {
                        checkProfileComplete(authUser, onProfileComplete, onProfileIncomplete)
                    } else {
                        // Para otros roles, ir directamente al dashboard
                        onSuccess(authUser)
                    }
                },
                onFailure = { error ->
                    _uiState.value = LoginUiState.Error(
                        error.message ?: "Error al iniciar sesión"
                    )
                }
            )
        }
    }
    
    private suspend fun checkProfileComplete(
        authUser: AuthUser,
        onProfileComplete: (AuthUser) -> Unit,
        onProfileIncomplete: (AuthUser) -> Unit
    ) {
        println("🔵 [LoginScreenModel] Verificando perfil completo para usuario: ${authUser.idUsuario}")
        getConductorByIdUseCase(authUser.idUsuario).fold(
            onSuccess = { conductor ->
                // Perfil completo → Ir al dashboard
                println("🟢 [LoginScreenModel] Perfil completo encontrado, navegando al dashboard")
                onProfileComplete(authUser)
            },
            onFailure = { error ->
                // Si el error es que no existe (404), el perfil no está completo
                // Si es otro error, también asumimos que no está completo por seguridad
                println("🟡 [LoginScreenModel] Perfil no completado o error: ${error.message}")
                onProfileIncomplete(authUser)
            }
        )
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

