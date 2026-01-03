package gas.control.project.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import gas.control.project.presentation.screenmodel.EstadosListScreenModel
import gas.control.project.presentation.screenmodel.EstadosListUiState
import org.koin.compose.koinInject

class EstadosListScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel: EstadosListScreenModel = koinInject()
        val uiState by screenModel.uiState.collectAsState()
        
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("Estados") },
                    actions = {
                        IconButton(onClick = { screenModel.refresh() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (val state = uiState) {
                    is EstadosListUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is EstadosListUiState.Success -> {
                        if (state.estados.isEmpty()) {
                            EmptyEstadosView(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            EstadosList(
                                estados = state.estados,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    is EstadosListUiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = { screenModel.refresh() },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadosList(
    estados: List<gas.control.project.domain.model.Estado>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(estados) { estado ->
            EstadoItem(estado = estado)
        }
    }
}

@Composable
private fun EstadoItem(
    estado: gas.control.project.domain.model.Estado
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = estado.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            estado.descripcion?.let { descripcion ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ID: ${estado.idEstado}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyEstadosView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No hay estados disponibles",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

