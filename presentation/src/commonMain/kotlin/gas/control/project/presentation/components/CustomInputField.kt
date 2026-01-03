package gas.control.project.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gas.control.project.presentation.theme.AppColors

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .width(313.dp)
            .height(39.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF1E1E1E)
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color(0xFF5C5C5C),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = androidx.compose.ui.Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(AppColors.Gray)
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}

