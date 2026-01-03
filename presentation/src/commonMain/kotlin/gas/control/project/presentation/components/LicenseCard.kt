package gas.control.project.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Recuadro con esquina superior derecha redondeada a 31px y las demás a 12px
 */
@Composable
fun LicenseCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .width(350.dp)
            .height(258.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 31.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .border(
                width = 1.dp,
                color = Color(0xFF16B900),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 31.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF757575)
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

