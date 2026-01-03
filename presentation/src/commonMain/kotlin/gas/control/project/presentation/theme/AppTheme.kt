package gas.control.project.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Colores del tema
object AppColors {
    val Gray = 0xFF757575
    val Red = 0xFFFF2621
    val White = 0xFFFFFFFF
    val Black = 0xFF000000
}

// Tipografía personalizada (usando fuente del sistema por ahora, luego se puede agregar Kanit)
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}

