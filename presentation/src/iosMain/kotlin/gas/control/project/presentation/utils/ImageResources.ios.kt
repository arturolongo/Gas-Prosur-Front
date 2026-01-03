package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual object ImageResources {
    @Composable
    actual fun iconUsuario(): Painter {
        // Para iOS, necesitamos usar el nombre del archivo sin extensión
        // y debe estar en Resources/
        return painterResource("icon_usuario")
    }
    
    @Composable
    actual fun iconEyeHidden(): Painter {
        return painterResource("icon_eye_hidden")
    }
    
    @Composable
    actual fun highwayBackground(): Painter {
        return painterResource("highway_background")
    }
    
    @Composable
    actual fun iconCarros(): Painter {
        return painterResource("icon_carros")
    }
    
    @Composable
    actual fun fondoAuto(): Painter {
        return painterResource("fondo_auto")
    }
    
    @Composable
    actual fun pickup(): Painter {
        return painterResource("pickup")
    }
    
    @Composable
    actual fun sedan(): Painter {
        return painterResource("sedan")
    }
    
    @Composable
    actual fun iconGas(): Painter {
        return painterResource("gas")
    }
    
    @Composable
    actual fun iconServicioAuto(): Painter {
        return painterResource("servicio_auto")
    }
    
    @Composable
    actual fun iconLicencia(): Painter {
        return painterResource("licencia")
    }
    
    @Composable
    actual fun iconLlave(): Painter {
        return painterResource("llave")
    }
    
    @Composable
    actual fun iconHistorial(): Painter {
        return painterResource("icon_historial")
    }
    
    @Composable
    actual fun iconAutos(): Painter {
        return painterResource("icon_autos")
    }
    
    @Composable
    actual fun iconServicios(): Painter {
        return painterResource("icon_servicios")
    }
    
    @Composable
    actual fun iconVelocimetro(): Painter {
        return painterResource("icon_velocimetro")
    }
    
    @Composable
    actual fun iconReloj(): Painter {
        return painterResource("icon_reloj")
    }
}

