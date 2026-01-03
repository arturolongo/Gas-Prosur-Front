package gas.control.project.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gas.control.project.presentation.screenmodel.ConductorDashboardScreenModel
import gas.control.project.presentation.screenmodel.DashboardUiState
import gas.control.project.presentation.utils.ImageResources
import gas.control.project.presentation.utils.ImageUrlHelper
import gas.control.project.presentation.utils.rememberImageFromUri
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class ConductorDashboardScreen(
    private val conductorId: Int
) : Screen {
    
    @Composable
    override fun Content() {
        val screenModel: ConductorDashboardScreenModel = koinInject(parameters = { parametersOf(conductorId) })
        val uiState by screenModel.uiState.collectAsState()
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is DashboardUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }
                is DashboardUiState.Success -> {
                    ConductorDashboardContent(conductor = state.conductor)
                }
            }
        }
    }
}

@Composable
fun ConductorDashboardContent(conductor: gas.control.project.domain.model.Conductor) {
    val navigator = LocalNavigator.currentOrThrow
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header rojo con foto de perfil y nombre
            ConductorHeader(
                nombre = conductor.nombre,
                apellido = conductor.apellido,
                fotoPerfilUrl = ImageUrlHelper.buildImageUrl(conductor.urlFotoPerfil)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Sección "Autos asignados"
            AutosAsignadosSection(navigator = navigator)
        
        // Resto del contenido (temporalmente comentado para continuar después)
        /*
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información de la licencia
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información de Licencia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575)
            )
            
            conductor.idLicencia?.let { idLicencia ->
                InfoRow(label = "ID Licencia", value = idLicencia)
            }
            
            conductor.tipoLicenciaCodigo?.let { tipoLicencia ->
                InfoRow(label = "Tipo de Licencia", value = tipoLicencia)
            }
            
            conductor.estadoExpedicionNombre?.let { estado ->
                InfoRow(label = "Estado de Expedición", value = estado)
            }
            
            conductor.fechaExpiracionLicencia?.let { fecha ->
                InfoRow(label = "Fecha de Expiración", value = fecha)
            }
            
            conductor.licenciaVencida?.let { vencida ->
                InfoRow(
                    label = "Estado",
                    value = if (vencida) "Vencida" else "Vigente",
                    valueColor = if (vencida) Color.Red else Color(0xFF16B900)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Estadísticas
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Estadísticas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF757575)
            )
            
            if (conductor.totalViajes != null) {
                InfoRow(label = "Total de Viajes", value = conductor.totalViajes.toString())
            }
            
            if (conductor.totalAutosAsignados != null) {
                InfoRow(label = "Autos Asignados", value = conductor.totalAutosAsignados.toString())
            }
        }
        */
        }
        
        // Botón "Prestar auto" (Floating Action Button)
        PrestarAutoButton(
            onClick = { /* TODO: Implementar prestar auto */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 90.dp) // 70dp (altura barra) + 20dp de espacio
        )
        
        // Barra de navegación inferior
        BottomNavigationBar(
            selectedTab = NavigationTab.AUTOS,
            onTabSelected = { /* TODO: Implementar navegación */ },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ConductorHeader(
    nombre: String,
    apellido: String,
    fotoPerfilUrl: String?
) {
    val fotoPerfilBitmap = rememberImageFromUri(fotoPerfilUrl)
    val headerShape = RoundedCornerShape(
        topStart = 35.dp,
        bottomStart = 35.dp,
        topEnd = 5.dp,
        bottomEnd = 32.dp
    )
    
    // Obtener solo el primer nombre y primer apellido
    val primerNombre = nombre.trim().split(" ").firstOrNull()?.takeIf { it.isNotBlank() } ?: nombre.trim()
    val primerApellido = apellido.trim().split(" ").firstOrNull()?.takeIf { it.isNotBlank() } ?: apellido.trim()
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 77.dp, end = 15.dp)
    ) {
        // Recuadro rojo con esquinas personalizadas y sombra
        Box(
            modifier = Modifier
                .width(275.dp)
                .height(53.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = headerShape,
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .clip(headerShape)
                .background(Color(0xFFFF2621))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 21.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Foto de perfil circular
                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    if (fotoPerfilBitmap != null) {
                        Image(
                            bitmap = fotoPerfilBitmap,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(
                            text = "${primerNombre.firstOrNull() ?: ""}${primerApellido.firstOrNull() ?: ""}",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Nombre (solo primer nombre y primer apellido)
                Text(
                    text = "$primerNombre $primerApellido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AutosAsignadosSection(navigator: cafe.adriel.voyager.navigator.Navigator) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, end = 23.dp)
    ) {
        // Título "Autos asignados" con icono
        // Posición y:151 desde el top de la pantalla, pero el header está en y:77
        // Entonces necesitamos: 151 - 77 - 53 (altura del header) = 21dp desde el header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 21.dp), // 151 - 77 - 53 = 21dp desde el bottom del header
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Autos asignados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF757575)
            )
            
            // Icono de carros (posición x:344, pero ajustado para estar alineado)
            Image(
                painter = ImageResources.iconCarros(),
                contentDescription = "Icono de carros",
                modifier = Modifier.size(25.dp)
            )
        }
        
        // Línea roja debajo
        // Separación de 11dp desde el título según el maquetado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(Color(0xFFFF2621))
        )
        
        // Cards de autos asignados
        // Primera card en y:212, línea roja en y:190, entonces: 212 - 190 = 22dp
        Spacer(modifier = Modifier.height(22.dp))
        
        // Card de ejemplo (temporal para ver el diseño)
        AutoCard(
            vehiculoNombre = "Nissan frontier 2025",
            esPickup = true,
            navigator = navigator,
            modifier = Modifier.padding(start = 10.dp) // 33 - 23 = 10dp desde el padding de la sección
        )
    }
}

@Composable
fun AutoCard(
    vehiculoNombre: String = "Nissan frontier 2025",
    esPickup: Boolean = true,
    navigator: cafe.adriel.voyager.navigator.Navigator,
    modifier: Modifier = Modifier
) {
    // Diseño base (iPhone 16): ancho ~393dp.
    // Importante: NO escalamos hacia abajo (en Android suele ser ~360dp), porque se ve “chiquito”.
    // Solo escalamos hacia arriba en pantallas más grandes.
    BoxWithConstraints(modifier = modifier) {
        val designWidth = 393f
        val scale = (maxWidth.value / designWidth).coerceIn(1.0f, 1.25f)
        
        fun dp(v: Float) = (v * scale).dp
        fun sp(v: Float) = (v * scale).sp
        
        // Un poco más alto para que el contenido respire y no se encime con el botón.
        val cardH = dp(112f)
        
        // Mantenemos el ancho total ~335, pero le damos 4dp extra al panel derecho (antes eran “slack”).
        // Le damos más aire al panel derecho para evitar cortes de texto,
        // manteniendo el ancho total de la card (335).
        val leftW = dp(130f)
        val rightW = dp(205f)
        val cardW = leftW + rightW
        
        Row(
            modifier = Modifier
                .width(cardW)
                .height(cardH)
        ) {
        // Imagen de fondo del auto
        // Bloque izquierdo: base 130dp de ancho (antes 137) para dar más espacio al panel derecho.
        val fondoShape = RoundedCornerShape(
            topStart = dp(12f),
            bottomStart = dp(12f),
            topEnd = 0.dp,
            bottomEnd = 0.dp
        )
        
        Box(
            modifier = Modifier
                .width(leftW)
                .height(cardH)
                .clip(fondoShape)
        ) {
            Image(
                painter = ImageResources.fondoAuto(),
                contentDescription = "Fondo auto",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Imagen del auto (Pickup o Sedan)
            // Como la imagen original es grande (1919x1080) y el auto está en el centro,
            // usamos padding negativo y escala para hacerla más grande
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (esPickup) ImageResources.pickup() else ImageResources.sedan(),
                    contentDescription = if (esPickup) "Pickup" else "Sedan",
                    modifier = Modifier
                        .width(dp(126f))
                        .height(dp(92f)),
                    contentScale = ContentScale.Fit
                )
            }
        }
        
        // Panel derecho con información
        Box(
            modifier = Modifier
                .width(rightW)
                .height(cardH)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = dp(12f),
                        bottomEnd = dp(12f)
                    )
                )
                .background(Color(0xFFD9D9D9))
        ) {
            // Iconos un poco más pequeños para que alineen visualmente con el texto (como en el maquetado)
            val iconSize = dp(10f)
            val iconTextGap = dp(5f)

            // Posicionamiento estilo maquetado (con escala) para que se vea idéntico, sin depender del wrap.
            Text(
                text = "Nissan frontier 2025",
                fontSize = sp(14f),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5C5C5C),
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = dp(10f), top = dp(8f))
            )
            
            // Gas (alineado icono-texto): una sola Row para que quede al ras.
            // El maquetado tiene el texto ~3px más arriba que el icono, por eso bajamos ligeramente el icono.
            Row(
                modifier = Modifier
                    .padding(start = dp(10f), top = dp(27f), end = dp(10f))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = ImageResources.iconGas(),
                    contentDescription = "Gas",
                    modifier = Modifier
                        .size(iconSize)
                )
                Spacer(modifier = Modifier.width(iconTextGap))
                Text(
                    text = "6.5 - 13 km/L",
                    fontSize = sp(11f),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5C5C5C),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
            
            // Servicio (alineado icono-texto): Row única
            Row(
                modifier = Modifier
                    .padding(start = dp(10f), top = dp(47f), end = dp(10f))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = ImageResources.iconServicioAuto(),
                    contentDescription = "Servicio",
                    modifier = Modifier
                        .size(iconSize)
                )
                Spacer(modifier = Modifier.width(iconTextGap))
                Text(
                    text = "Servicio en:",
                    fontSize = sp(11f),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5C5C5C),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dp(6f)))
                Text(
                    text = "100,000km",
                    fontSize = sp(11f),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFF2621),
                    maxLines = 1
                )
            }
            
            // Matrícula
            Row(
                modifier = Modifier
                    .padding(start = dp(10f), top = dp(64f), end = dp(10f))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = ImageResources.iconLicencia(),
                    contentDescription = "Licencia",
                    modifier = Modifier
                        .size(iconSize)
                )
                Spacer(modifier = Modifier.width(iconTextGap))
                Text(
                    text = "Matricula:",
                    fontSize = sp(11f),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5C5C5C),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(dp(6f)))
                Text(
                    text = "DC - 999 - Z",
                    fontSize = sp(11f),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5C5C5C),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Botón (abajo derecha). Quitamos “Disponible” como pediste.
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    // Pegado a la esquina inferior derecha del panel
                    .padding(end = dp(0f), bottom = dp(0f))
                    .width(dp(115f))
                    .height(dp(22f))
                    .clip(
                        RoundedCornerShape(
                            topStart = dp(2f),
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = dp(12f)
                        )
                    )
                    .background(Color(0xFFFF2621))
                    .clickable { 
                        navigator.push(ViajeActivoScreen(
                            vehiculoNombre = vehiculoNombre,
                            esPickup = esPickup
                        ))
                    }
                    .padding(horizontal = dp(8f)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Iniciar viaje",
                    fontSize = sp(12f),
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(dp(4f)))
                Image(
                    painter = ImageResources.iconLlave(),
                    contentDescription = "Iniciar viaje",
                    modifier = Modifier.size(dp(10f))
                )
            }
        }
    }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF757575)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF757575),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            fontWeight = FontWeight.Normal
        )
    }
}

enum class NavigationTab {
    HISTORIAL,
    AUTOS,
    SERVICIOS
}

@Composable
fun BottomNavigationBar(
    selectedTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF2C2C2C))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Historial
            NavigationItem(
                icon = ImageResources.iconHistorial(),
                label = "Historial",
                isSelected = selectedTab == NavigationTab.HISTORIAL,
                onClick = { onTabSelected(NavigationTab.HISTORIAL) }
            )
            
            // Autos
            NavigationItem(
                icon = ImageResources.iconAutos(),
                label = "Autos",
                isSelected = selectedTab == NavigationTab.AUTOS,
                onClick = { onTabSelected(NavigationTab.AUTOS) }
            )
            
            // Servicios
            NavigationItem(
                icon = ImageResources.iconServicios(),
                label = "Servicios",
                isSelected = selectedTab == NavigationTab.SERVICIOS,
                onClick = { onTabSelected(NavigationTab.SERVICIOS) }
            )
        }
    }
}

@Composable
fun NavigationItem(
    icon: androidx.compose.ui.graphics.painter.Painter,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(if (isSelected) 28.dp else 24.dp),
            colorFilter = if (isSelected) null else ColorFilter.tint(Color(0xFF757575))
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) Color.White else Color(0xFF757575)
        )
    }
}

@Composable
fun PrestarAutoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF16B900))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Prestar auto",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "→",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

