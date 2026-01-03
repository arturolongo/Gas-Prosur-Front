package gas.control.project.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gas.control.project.presentation.utils.ImageResources

class ViajeActivoScreen(
    private val vehiculoNombre: String = "Nissan frontier 2025",
    private val esPickup: Boolean = true
) : Screen {
    
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Mapa (65% de la pantalla) - Placeholder por ahora
                MapPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.65f)
                )
                
                // Panel inferior (35% de la pantalla)
                TripInfoPanel(
                    vehiculoNombre = vehiculoNombre,
                    esPickup = esPickup,
                    onRegistrarGasolina = {
                        // TODO: Implementar registrar gasolina
                    },
                    onConcluirViaje = {
                        navigator.pop()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.35f)
                )
            }
        }
    }
}

@Composable
fun MapPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🗺️",
                fontSize = 48.sp
            )
            Text(
                text = "Mapa (placeholder)",
                fontSize = 16.sp,
                color = Color(0xFF757575),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Integración de mapa pendiente",
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}

@Composable
fun TripInfoPanel(
    vehiculoNombre: String,
    esPickup: Boolean,
    onRegistrarGasolina: () -> Unit,
    onConcluirViaje: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Datos simulados del viaje
    val kilometros = "150"
    val velocidadPromedio = "100"
    val gastosGasolina = "$2500.00"
    val tiempoViaje = "1:20:54"
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3F4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Parte superior: Imagen del auto e información
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Imagen del auto
                Image(
                    painter = if (esPickup) ImageResources.pickup() else ImageResources.sedan(),
                    contentDescription = "Vehículo",
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Fit
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Información del vehículo
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = vehiculoNombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Kilometros de viaje actual: ",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${kilometros}km",
                            fontSize = 12.sp,
                            color = Color(0xFFFF2621),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Estadísticas del viaje
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Velocidad promedio
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = ImageResources.iconVelocimetro(),
                        contentDescription = "Velocímetro",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Velocidad promedio de viaje: ",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Text(
                            text = "$velocidadPromedio km/h",
                            fontSize = 12.sp,
                            color = Color(0xFFFF2621),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Gastos de gasolina
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = ImageResources.iconGas(),
                        contentDescription = "Gasolina",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Gastos de gasolina actuales: ",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Text(
                            text = gastosGasolina,
                            fontSize = 12.sp,
                            color = Color(0xFFFF2621),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Tiempo de viaje
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = ImageResources.iconReloj(),
                        contentDescription = "Reloj",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tiempo actual de viaje: ",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Text(
                            text = tiempoViaje,
                            fontSize = 12.sp,
                            color = Color(0xFFFF2621),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón "Registrar gasolina"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFF2621),
                            shape = RoundedCornerShape(9.dp)
                        )
                        .clickable(onClick = onRegistrarGasolina),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Registrar gasolina",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF2621),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                
                // Botón "Concluir viaje"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color(0xFFFF2621))
                        .clickable(onClick = onConcluirViaje),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Concluir viaje",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun TripStatRow(
    icon: androidx.compose.ui.graphics.painter.Painter,
    label: String,
    value: String,
    iconSize: androidx.compose.ui.unit.Dp = 16.dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color(0xFFFF2621),
            fontWeight = FontWeight.Medium
        )
    }
}

