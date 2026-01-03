package gas.control.project.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gas.control.project.presentation.screenmodel.UserFormScreenModel
import gas.control.project.presentation.screenmodel.UserFormUiState
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class UserFormScreen(private val userId: String? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: UserFormScreenModel = koinInject(parameters = { parametersOf(userId) })
        val uiState by screenModel.uiState.collectAsState()
        val name by screenModel.name.collectAsState()
        val email by screenModel.email.collectAsState()
        
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text(if (userId == null) "Nuevo Usuario" else "Editar Usuario") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
                    is UserFormUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UserFormUiState.Editing -> {
                        UserFormContent(
                            name = name,
                            email = email,
                            onNameChange = { screenModel.updateName(it) },
                            onEmailChange = { screenModel.updateEmail(it) },
                            onSave = { screenModel.saveUser { navigator.pop() } },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    is UserFormUiState.Saving -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UserFormUiState.Success -> {
                        // Se maneja en el callback de saveUser
                    }
                    is UserFormUiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = { screenModel.saveUser { navigator.pop() } },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserFormContent(
    name: String,
    email: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && email.isNotBlank() && email.contains("@")
        ) {
            Text("Guardar")
        }
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

