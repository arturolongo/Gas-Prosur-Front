package gas.control.project.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class CameraManager {
    actual fun takePhotoFromCamera(onPhotoTaken: (String?) -> Unit) {
        // TODO: Implementar para iOS
        onPhotoTaken(null)
    }
    
    actual fun takePhotoFromFrontCamera(onPhotoTaken: (String?) -> Unit) {
        // TODO: Implementar para iOS
        onPhotoTaken(null)
    }
}

@Composable
actual fun rememberCameraManager(): CameraManager {
    return remember { CameraManager() }
}

