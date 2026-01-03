package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.model.User
import gas.control.project.domain.usecase.DeleteUserUseCase
import gas.control.project.domain.usecase.GetAllUsersUseCase
import gas.control.project.domain.usecase.SyncUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserListScreenModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val syncUsersUseCase: SyncUsersUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        screenModelScope.launch {
            _uiState.value = UserListUiState.Loading
            try {
                getAllUsersUseCase().collect { users ->
                    _uiState.value = UserListUiState.Success(users)
                }
            } catch (e: Exception) {
                _uiState.value = UserListUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun syncUsers() {
        screenModelScope.launch {
            _uiState.value = UserListUiState.Loading
            syncUsersUseCase().fold(
                onSuccess = {
                    // Los usuarios se cargarán automáticamente a través del Flow
                },
                onFailure = { error ->
                    _uiState.value = UserListUiState.Error(error.message ?: "Error al sincronizar")
                }
            )
        }
    }
    
    fun deleteUser(userId: String) {
        screenModelScope.launch {
            deleteUserUseCase(userId).fold(
                onSuccess = {
                    // Los usuarios se actualizarán automáticamente a través del Flow
                },
                onFailure = { error ->
                    _uiState.value = UserListUiState.Error(error.message ?: "Error al eliminar usuario")
                }
            )
        }
    }
}

sealed class UserListUiState {
    object Loading : UserListUiState()
    data class Success(val users: List<User>) : UserListUiState()
    data class Error(val message: String) : UserListUiState()
}

