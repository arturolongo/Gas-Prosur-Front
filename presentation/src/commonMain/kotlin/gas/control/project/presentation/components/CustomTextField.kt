package gas.control.project.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gas.control.project.presentation.theme.AppColors

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(AppColors.Gray)
            )
        },
        modifier = modifier
            .width(339.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(AppColors.Red).copy(alpha = 0.97f),
            focusedBorderColor = Color(AppColors.Red).copy(alpha = 0.97f),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedTextColor = Color(AppColors.Gray),
            focusedTextColor = Color(AppColors.Gray)
        ),
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = if (isPassword) {
            androidx.compose.ui.text.input.PasswordVisualTransformation()
        } else {
            androidx.compose.ui.text.input.VisualTransformation.None
        }
    )
}

