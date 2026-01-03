package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import gas.control.project.presentation.R

actual object ImageResources {
    @Composable
    actual fun iconUsuario(): Painter {
        return painterResource(id = R.drawable.icon_usuario)
    }
    
    @Composable
    actual fun iconEyeHidden(): Painter {
        return painterResource(id = R.drawable.icon_eye_hidden)
    }
    
    @Composable
    actual fun highwayBackground(): Painter {
        return painterResource(id = R.drawable.highway_background)
    }
    
    @Composable
    actual fun iconCarros(): Painter {
        return painterResource(id = R.drawable.icon_carros)
    }
    
    @Composable
    actual fun fondoAuto(): Painter {
        return painterResource(id = R.drawable.fondo_auto)
    }
    
    @Composable
    actual fun pickup(): Painter {
        return painterResource(id = R.drawable.pickup)
    }
    
    @Composable
    actual fun sedan(): Painter {
        return painterResource(id = R.drawable.sedan)
    }
    
    @Composable
    actual fun iconGas(): Painter {
        return painterResource(id = R.drawable.gas)
    }
    
    @Composable
    actual fun iconServicioAuto(): Painter {
        return painterResource(id = R.drawable.servicio_auto)
    }
    
    @Composable
    actual fun iconLicencia(): Painter {
        return painterResource(id = R.drawable.licencia)
    }
    
    @Composable
    actual fun iconLlave(): Painter {
        return painterResource(id = R.drawable.llave)
    }
    
    @Composable
    actual fun iconHistorial(): Painter {
        return painterResource(id = R.drawable.icon_historial)
    }
    
    @Composable
    actual fun iconAutos(): Painter {
        return painterResource(id = R.drawable.icon_autos)
    }
    
    @Composable
    actual fun iconServicios(): Painter {
        return painterResource(id = R.drawable.icon_servicios)
    }
    
    @Composable
    actual fun iconVelocimetro(): Painter {
        return painterResource(id = R.drawable.icon_velocimetro)
    }
    
    @Composable
    actual fun iconReloj(): Painter {
        return painterResource(id = R.drawable.icon_reloj)
    }
}

