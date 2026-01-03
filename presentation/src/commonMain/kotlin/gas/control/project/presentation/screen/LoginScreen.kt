package gas.control.project.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gas.control.project.presentation.utils.ImageResources
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gas.control.project.presentation.components.CustomButton
import gas.control.project.presentation.components.CustomTextField
import gas.control.project.presentation.screenmodel.LoginScreenModel
import gas.control.project.presentation.screenmodel.LoginUiState
import gas.control.project.presentation.theme.AppColors
import org.koin.compose.koinInject
import gas.control.project.presentation.screen.ConductorDashboardScreen
import gas.control.project.presentation.screen.ConductorOnboardingScreen
import gas.control.project.presentation.screen.EstadosListScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: LoginScreenModel = koinInject()
        val uiState by screenModel.uiState.collectAsState()
        val usuario by screenModel.usuario.collectAsState()
        val password by screenModel.password.collectAsState()
        val passwordVisible by screenModel.passwordVisible.collectAsState()
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {

            Image(
                painter = ImageResources.highwayBackground(),
                contentDescription = "Carretera",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(292.dp)
                    .offset(y = 20.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 31.dp)
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(modifier = Modifier.height(120.dp))
                
                Text(
                    text = "Sistema Integral de\nGestión Vehicular",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(AppColors.Gray)
                    )
                )

                Spacer(modifier = Modifier.height(80.dp))

                CustomTextField(
                    value = usuario,
                    onValueChange = { screenModel.updateUsuario(it) },
                    placeholder = "Usuario",
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Image(
                            painter = ImageResources.iconUsuario(),
                            contentDescription = "Usuario",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(13.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { screenModel.updatePassword(it) },
                    placeholder = "Contraseña",
                    modifier = Modifier.fillMaxWidth(),
                    isPassword = !passwordVisible,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Contraseña",
                            modifier = Modifier.size(20.dp),
                            tint = Color(AppColors.Gray)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { screenModel.togglePasswordVisibility() }) {
                            if (passwordVisible) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Ocultar",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color(AppColors.Gray)
                                )
                            } else {
                                Image(
                                    painter = ImageResources.iconEyeHidden(),
                                    contentDescription = "Mostrar",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(13.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (val state = uiState) {
                        is LoginUiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color(AppColors.Red)
                            )
                        }
                        else -> {
                            CustomButton(
                                text = "Ingresar",
                                onClick = {
                                    screenModel.login(
                                        onSuccess = { authUser ->
                                            // Para roles que no son Conductor, ir al dashboard
                                            navigator.push(EstadosListScreen())
                                        },
                                        onProfileComplete = { authUser ->
                                            // Perfil completo → Ir al dashboard
                                            navigator.push(ConductorDashboardScreen(conductorId = authUser.idUsuario))
                                        },
                                        onProfileIncomplete = { authUser ->
                                            // Perfil NO completado → Mostrar pantalla de completar perfil
                                            navigator.push(ConductorOnboardingScreen(
                                                nombre = authUser.nombre,
                                                idUsuario = authUser.idUsuario
                                            ))
                                        }
                                    )
                                },
                                enabled = true 
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { /* TODO: Implementar recuperación de contraseña */ },
                    modifier = Modifier.align(Alignment.Start),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(AppColors.Red)
                    )
                }
            }

            (uiState as? LoginUiState.Error)?.let { errorState ->
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Error") },
                    text = { Text(errorState.message) },
                    confirmButton = {
                        TextButton(onClick = { }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

