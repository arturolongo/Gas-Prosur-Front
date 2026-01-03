package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.model.User
import gas.control.project.domain.usecase.CreateUserUseCase
import gas.control.project.domain.usecase.GetUserByIdUseCase
import gas.control.project.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class UserFormScreenModel(
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val userId: String? = null
) : ScreenModel {
    
    private val _uiState = MutableStateFlow<UserFormUiState>(UserFormUiState.Loading)
    val uiState: StateFlow<UserFormUiState> = _uiState.asStateFlow()
    
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()
    
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()
    
    init {
        if (userId != null) {
            loadUser(userId)
        } else {
            _uiState.value = UserFormUiState.Editing
        }
    }
    
    private fun loadUser(id: String) {
        screenModelScope.launch {
            _uiState.value = UserFormUiState.Loading
            getUserByIdUseCase(id)?.let { user ->
                _name.value = user.name
                _email.value = user.email
                _uiState.value = UserFormUiState.Editing
            } ?: run {
                _uiState.value = UserFormUiState.Error("Usuario no encontrado")
            }
        }
    }
    
    fun updateName(newName: String) {
        _name.value = newName
    }
    
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }
    
    fun saveUser(onSuccess: () -> Unit) {
        screenModelScope.launch {
            _uiState.value = UserFormUiState.Saving
            
            val user = User(
                id = userId ?: generateId(),
                name = _name.value.trim(),
                email = _email.value.trim(),
                createdAt = if (userId == null) Clock.System.now() else null,
                updatedAt = Clock.System.now()
            )
            
            val result = if (userId == null) {
                createUserUseCase(user)
            } else {
                updateUserUseCase(user)
            }
            
            result.fold(
                onSuccess = {
                    _uiState.value = UserFormUiState.Success
                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.value = UserFormUiState.Error(error.message ?: "Error al guardar usuario")
                }
            )
        }
    }
    
    private fun generateId(): String {
        return "user_${System.currentTimeMillis()}"
    }
}

sealed class UserFormUiState {
    object Loading : UserFormUiState()
    object Editing : UserFormUiState()
    object Saving : UserFormUiState()
    object Success : UserFormUiState()
    data class Error(val message: String) : UserFormUiState()
}

