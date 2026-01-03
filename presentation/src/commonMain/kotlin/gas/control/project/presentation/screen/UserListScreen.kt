package gas.control.project.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gas.control.project.presentation.screenmodel.UserListScreenModel
import gas.control.project.presentation.screenmodel.UserListUiState
import org.koin.compose.koinInject

class UserListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: UserListScreenModel = koinInject()
        val uiState by screenModel.uiState.collectAsState()
        
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("Usuarios") },
                    actions = {
                        IconButton(onClick = { screenModel.syncUsers() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Sincronizar")
                        }
                        IconButton(onClick = { navigator.push(UserFormScreen()) }) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar usuario")
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
                    is UserListUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UserListUiState.Success -> {
                        if (state.users.isEmpty()) {
                            EmptyUsersView(
                                onAddClick = { navigator.push(UserFormScreen()) },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            UserList(
                                users = state.users,
                                onUserClick = { userId ->
                                    navigator.push(UserFormScreen(userId))
                                },
                                onDeleteClick = { userId ->
                                    screenModel.deleteUser(userId)
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    is UserListUiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = { screenModel.loadUsers() },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserList(
    users: List<gas.control.project.domain.model.User>,
    onUserClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(
                user = user,
                onClick = { onUserClick(user.id) },
                onDelete = { onDeleteClick(user.id) }
            )
        }
    }
}

@Composable
private fun UserItem(
    user: gas.control.project.domain.model.User,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Text(
                    text = "🗑️",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
private fun EmptyUsersView(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No hay usuarios",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar primer usuario")
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

