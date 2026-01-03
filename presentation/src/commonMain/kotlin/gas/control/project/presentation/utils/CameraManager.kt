package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable

/**
 * Interfaz para manejar el acceso a la cámara en multiplataforma
 */
expect class CameraManager {
    /**
     * Abre la cámara trasera para tomar una foto
     * @param onPhotoTaken Callback con la ruta de la foto tomada
     */
    fun takePhotoFromCamera(onPhotoTaken: (String?) -> Unit)
    
    /**
     * Abre la cámara frontal para tomar una foto
     * @param onPhotoTaken Callback con la ruta de la foto tomada
     */
    fun takePhotoFromFrontCamera(onPhotoTaken: (String?) -> Unit)
}

@Composable
expect fun rememberCameraManager(): CameraManager

