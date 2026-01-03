package gas.control.project.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

actual object ImageUtils {
    /**
     * Convierte un Uri a Base64 con formato data URI
     * @param context Context de Android
     * @param imageUri Uri de la imagen
     * @param maxWidth Ancho máximo (opcional, para reducir tamaño)
     * @param maxHeight Alto máximo (opcional, para reducir tamaño)
     * @param quality Calidad de compresión (0-100)
     * @return String Base64 con formato "data:image/jpeg;base64,..." o null si hay error
     */
    actual fun uriToBase64(
        context: Any,
        imageUri: Any,
        maxWidth: Int,
        maxHeight: Int,
        quality: Int
    ): String? {
        return try {
            val androidContext = context as? Context ?: return null
            val androidUri = imageUri as? Uri ?: return null
            
            // Obtener bitmap desde Uri
            val inputStream: InputStream? = androidContext.contentResolver.openInputStream(androidUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            bitmap?.let {
                // Redimensionar si es necesario
                val resizedBitmap = resizeBitmap(it, maxWidth, maxHeight)
                bitmapToBase64(resizedBitmap, quality)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Convierte un Bitmap a Base64 con formato data URI
     */
    private fun bitmapToBase64(
        bitmap: Bitmap,
        quality: Int = 80
    ): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val byteArray = outputStream.toByteArray()
        val base64String = Base64.encodeToString(byteArray, Base64.NO_WRAP)
        
        return "data:image/jpeg;base64,$base64String"
    }
    
    /**
     * Redimensiona un bitmap manteniendo la proporción
     */
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        
        if (width <= maxWidth && height <= maxHeight) {
            return bitmap
        }
        
        val ratio = minOf(
            maxWidth.toFloat() / width,
            maxHeight.toFloat() / height
        )
        
        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()
        
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
    
    /**
     * Obtiene un Bitmap desde un Uri
     */
    actual fun uriToBitmap(context: Any, imageUri: Any): Any? {
        return try {
            val androidContext = context as? Context ?: return null
            val androidUri = imageUri as? Uri ?: return null
            
            val inputStream: InputStream? = androidContext.contentResolver.openInputStream(androidUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

