package gas.control.project.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gas.control.project.presentation.utils.ImageResources
import cafe.adriel.voyager.core.screen.Screen
import gas.control.project.presentation.components.CustomInputField
import gas.control.project.presentation.screenmodel.ConductorOnboardingScreenModel
import gas.control.project.presentation.theme.AppColors
import gas.control.project.presentation.utils.rememberCameraManager
import gas.control.project.presentation.utils.ImageUtils
import gas.control.project.presentation.utils.rememberImageFromUri
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class ConductorOnboardingScreen(
    private val nombre: String,
    private val idUsuario: Int
) : Screen {
    
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ConductorOnboardingScreenModel = koinInject(parameters = { parametersOf(nombre, idUsuario) })
        val cameraManager = rememberCameraManager()
        val context = LocalContext.current
        
        val licenciaFotoPath by screenModel.licenciaFotoPath.collectAsState()
        val fotoPerfilPath by screenModel.fotoPerfilPath.collectAsState()
        val nombreCompleto by screenModel.nombreCompleto.collectAsState()
        val estadoExpedicion by screenModel.estadoExpedicion.collectAsState()
        val tipoLicencia by screenModel.tipoLicencia.collectAsState()
        val anoExpiracion by screenModel.anoExpiracion.collectAsState()
        val licenciaCompleta by screenModel.licenciaCompleta.collectAsState(false)
        val uiState by screenModel.uiState.collectAsState()
        
        // Cargar imagen de perfil
        val fotoPerfilBitmap = rememberImageFromUri(fotoPerfilPath)
        
        // Snackbar para mostrar errores
        val snackbarHostState = remember { SnackbarHostState() }
        
        // Mostrar error cuando ocurra
        LaunchedEffect(uiState) {
            if (uiState is gas.control.project.presentation.screenmodel.OnboardingUiState.Error) {
                val errorState = uiState as gas.control.project.presentation.screenmodel.OnboardingUiState.Error
                snackbarHostState.showSnackbar(
                    message = errorState.message,
                    duration = SnackbarDuration.Long
                )
            }
        }
        
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Imagen de fondo (carretera) - Pegada al fondo
            Image(
                painter = ImageResources.highwayBackground(),
                contentDescription = "Carretera",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 292.dp)
                    .offset(y = 20.dp),
                contentScale = ContentScale.FillBounds
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .systemBarsPadding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                // Saludo "Hola [Nombre]!"
                Text(
                    text = "Hola $nombre!",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(AppColors.Gray)
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Texto descriptivo
                Text(
                    text = "Completa estos datos para comenzar a utlizar la aplicacion :)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(AppColors.Gray)
                    )
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                // Recuadro de licencia (solo se muestra si ya se tomó la foto)
                if (licenciaFotoPath != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 31.dp,
                                    bottomEnd = 12.dp,
                                    bottomStart = 12.dp
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFF16B900),
                                shape = RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 31.dp,
                                    bottomEnd = 12.dp,
                                    bottomStart = 12.dp
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Subir licencia de conducir",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(AppColors.Gray)
                                    )
                                )
                                
                                // Check verde si está completa
                                if (licenciaCompleta) {
                                    Box(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF16B900)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Completo",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Label "Nombre completo :"
                            Text(
                                text = "Nombre completo :",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(AppColors.Gray)
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Input de nombre completo
                            CustomInputField(
                                value = nombreCompleto,
                                onValueChange = { screenModel.updateNombreCompleto(it) },
                                placeholder = "Ingresa tu nombre completo",
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Fila con Estado de expedición y Tipo
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Estado de expedición
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Estado de expedición:",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(AppColors.Gray)
                                        )
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    CustomInputField(
                                        value = estadoExpedicion,
                                        onValueChange = { screenModel.updateEstadoExpedicion(it) },
                                        placeholder = "Estado",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                
                                // Tipo
                                Column(modifier = Modifier.width(131.dp)) {
                                    Text(
                                        text = "Tipo:",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(AppColors.Gray)
                                        )
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    CustomInputField(
                                        value = tipoLicencia,
                                        onValueChange = { screenModel.updateTipoLicencia(it) },
                                        placeholder = "Tipo",
                                        modifier = Modifier.width(131.dp)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Año de expiración
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Año de expiración:",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(AppColors.Gray)
                                    )
                                )
                                
                                CustomInputField(
                                    value = anoExpiracion,
                                    onValueChange = { screenModel.updateAnoExpiracion(it) },
                                    placeholder = "Año",
                                    modifier = Modifier.width(132.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Botón "Subir licencia de conducir" (solo si no hay foto)
                    Button(
                        onClick = {
                            screenModel.onSubirLicenciaClick {
                                cameraManager.takePhotoFromCamera { photoPath ->
                                    screenModel.onLicenciaFotoTaken(photoPath)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp)
                            .border(
                                width = 1.dp,
                                color = Color(AppColors.Red),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ) {
                        Text(
                            text = "Subir licencia de conducir",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(AppColors.Gray)
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Recuadro de foto de perfil
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 31.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 12.dp
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF16B900),
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 31.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 12.dp
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Foto de perfil",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(AppColors.Gray)
                                )
                            )
                            
                            // Check verde si hay foto
                            if (fotoPerfilPath != null) {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF16B900)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completo",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        if (fotoPerfilPath != null && fotoPerfilBitmap != null) {
                            // Mostrar foto de perfil
                            Image(
                                bitmap = fotoPerfilBitmap,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(109.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Botón "Tomar nuevamente"
                            Button(
                                onClick = {
                                    screenModel.onEstablecerFotoPerfilClick {
                                        cameraManager.takePhotoFromFrontCamera { photoPath ->
                                            screenModel.onFotoPerfilTaken(photoPath)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .width(145.dp)
                                    .height(39.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF939393)
                                )
                            ) {
                                Text(
                                    text = "Tomar nuevamente",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White
                                    )
                                )
                            }
                        } else {
                            // Botón para tomar foto por primera vez
                            Button(
                                onClick = {
                                    screenModel.onEstablecerFotoPerfilClick {
                                        cameraManager.takePhotoFromFrontCamera { photoPath ->
                                            screenModel.onFotoPerfilTaken(photoPath)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF939393)
                                )
                            ) {
                                Text(
                                    text = "Tomar foto de perfil",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Botón Guardar
                Button(
                    onClick = {
                        // Convertir imágenes a Base64
                        val fotoLicenciaBase64 = licenciaFotoPath?.let { uriString ->
                            try {
                                val uri = Uri.parse(uriString)
                                ImageUtils.uriToBase64(context as Any, uri as Any)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                null
                            }
                        }
                        
                        val fotoPerfilBase64 = fotoPerfilPath?.let { uriString ->
                            try {
                                val uri = Uri.parse(uriString)
                                ImageUtils.uriToBase64(context as Any, uri as Any)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                null
                            }
                        }
                        
                        screenModel.onGuardar(
                            fotoLicenciaBase64 = fotoLicenciaBase64,
                            fotoPerfilBase64 = fotoPerfilBase64,
                            onSuccess = {
                                // Perfil completado exitosamente → Ir al dashboard
                                // TODO: Cambiar a DashboardScreen cuando esté listo
                                navigator.replace(EstadosListScreen())
                            },
                            onError = { errorMessage ->
                                // El error se mostrará a través del Snackbar
                                println("🔴 [ConductorOnboardingScreen] Error recibido: $errorMessage")
                            }
                        )
                    },
                    enabled = uiState !is gas.control.project.presentation.screenmodel.OnboardingUiState.Loading,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(159.dp)
                        .height(39.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0xFF16B900),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    )
                ) {
                    Text(
                        text = "Guardar :)",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF16B900)
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        }
    }
}
