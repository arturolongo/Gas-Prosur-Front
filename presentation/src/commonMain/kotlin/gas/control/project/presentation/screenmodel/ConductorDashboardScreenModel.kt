package gas.control.project.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import gas.control.project.domain.model.Conductor
import gas.control.project.domain.usecase.GetConductorByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConductorDashboardScreenModel(
    private val conductorId: Int,
    private val getConductorByIdUseCase: GetConductorByIdUseCase
) : ScreenModel {
    
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadConductor()
    }
    
    private fun loadConductor() {
        screenModelScope.launch {
            getConductorByIdUseCase(conductorId).fold(
                onSuccess = { conductor ->
                    _uiState.value = DashboardUiState.Success(conductor)
                },
                onFailure = { error ->
                    _uiState.value = DashboardUiState.Error(
                        error.message ?: "Error al cargar los datos"
                    )
                }
            )
        }
    }
    
    fun retry() {
        _uiState.value = DashboardUiState.Loading
        loadConductor()
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val conductor: Conductor) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

