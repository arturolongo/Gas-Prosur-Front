package gas.control.project.presentation.utils

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Utilidades para manejo de imágenes multiplataforma
 */
expect object ImageUtils {
    /**
     * Convierte un Uri a Base64 con formato data URI
     * @param context Context de la plataforma
     * @param imageUri Uri de la imagen
     * @param maxWidth Ancho máximo (opcional, para reducir tamaño)
     * @param maxHeight Alto máximo (opcional, para reducir tamaño)
     * @param quality Calidad de compresión (0-100)
     * @return String Base64 con formato "data:image/jpeg;base64,..." o null si hay error
     */
    fun uriToBase64(
        context: Any,
        imageUri: Any,
        maxWidth: Int = 1920,
        maxHeight: Int = 1920,
        quality: Int = 80
    ): String?
    
    /**
     * Obtiene un Bitmap desde un Uri (solo Android)
     */
    fun uriToBitmap(context: Any, imageUri: Any): Any?
}

