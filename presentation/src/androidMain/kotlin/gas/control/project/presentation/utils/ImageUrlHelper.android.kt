package gas.control.project.presentation.utils

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

actual object ImageUrlHelper : KoinComponent {
    private val baseUrl: String by inject(named("baseUrl"))
    
    actual fun buildImageUrl(relativePath: String?): String? {
        return if (relativePath.isNullOrBlank()) {
            null
        } else {
            // El baseUrl ya incluye /api, así que necesitamos construir la URL correcta
            // Las imágenes están en /uploads/ según la documentación
            val cleanBaseUrl = baseUrl.removeSuffix("/api")
            val cleanPath = relativePath.removePrefix("/")
            "$cleanBaseUrl/uploads/$cleanPath"
        }
    }
}

