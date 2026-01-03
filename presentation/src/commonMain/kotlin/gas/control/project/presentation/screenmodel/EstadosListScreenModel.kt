package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.model.Estado
import gas.control.project.domain.usecase.GetEstadosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EstadosListScreenModel(
    private val getEstadosUseCase: GetEstadosUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow<EstadosListUiState>(EstadosListUiState.Loading)
    val uiState: StateFlow<EstadosListUiState> = _uiState.asStateFlow()
    
    init {
        loadEstados()
    }
    
    fun loadEstados() {
        screenModelScope.launch {
            _uiState.value = EstadosListUiState.Loading
            getEstadosUseCase().fold(
                onSuccess = { estados ->
                    _uiState.value = EstadosListUiState.Success(estados)
                },
                onFailure = { error ->
                    _uiState.value = EstadosListUiState.Error(
                        error.message ?: "Error desconocido al cargar estados"
                    )
                }
            )
        }
    }
    
    fun refresh() {
        loadEstados()
    }
}

sealed class EstadosListUiState {
    object Loading : EstadosListUiState()
    data class Success(val estados: List<Estado>) : EstadosListUiState()
    data class Error(val message: String) : EstadosListUiState()
}

