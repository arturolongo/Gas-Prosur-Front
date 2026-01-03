package gas.control.project.presentation.utils

actual object ImageUtils {
    /**
     * Convierte un Uri a Base64 con formato data URI
     * TODO: Implementar para iOS
     */
    actual fun uriToBase64(
        context: Any,
        imageUri: Any,
        maxWidth: Int,
        maxHeight: Int,
        quality: Int
    ): String? {
        // TODO: Implementar conversión de imagen a Base64 en iOS
        return null
    }
    
    /**
     * Obtiene un Bitmap desde un Uri
     * TODO: Implementar para iOS
     */
    actual fun uriToBitmap(context: Any, imageUri: Any): Any? {
        // TODO: Implementar carga de imagen desde URI en iOS
        return null
    }
}

