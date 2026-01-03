package gas.control.project.presentation.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

actual class CameraManager(
    private val cameraLauncher: ActivityResultLauncher<Uri>,
    private val frontCameraLauncher: ActivityResultLauncher<Uri>,
    private val context: Context,
    private val callbackState: MutableState<((String?) -> Unit)?>,
    private val permissionLauncher: ActivityResultLauncher<String>,
    private val permissionCallbackState: MutableState<((Boolean) -> Unit)?>,
    private val currentPhotoUriState: MutableState<Uri?>
) {
    
    actual fun takePhotoFromCamera(onPhotoTaken: (String?) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            callbackState.value = onPhotoTaken
            val photoUri = createImageUri()
            currentPhotoUriState.value = photoUri
            photoUri?.let { cameraLauncher.launch(it) }
        } else {
            permissionCallbackState.value = { granted ->
                if (granted) {
                    callbackState.value = onPhotoTaken
                    val photoUri = createImageUri()
                    currentPhotoUriState.value = photoUri
                    photoUri?.let { cameraLauncher.launch(it) }
                } else {
                    onPhotoTaken(null)
                }
            }
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    
    actual fun takePhotoFromFrontCamera(onPhotoTaken: (String?) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            callbackState.value = onPhotoTaken
            val photoUri = createImageUri()
            currentPhotoUriState.value = photoUri
            photoUri?.let { frontCameraLauncher.launch(it) }
        } else {
            permissionCallbackState.value = { granted ->
                if (granted) {
                    callbackState.value = onPhotoTaken
                    val photoUri = createImageUri()
                    currentPhotoUriState.value = photoUri
                    photoUri?.let { frontCameraLauncher.launch(it) }
                } else {
                    onPhotoTaken(null)
                }
            }
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    
    private fun createImageUri(): Uri? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFileName = "JPEG_${timeStamp}_"
            val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
            
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        } catch (e: Exception) {
            // Si FileProvider falla, usar MediaStore como alternativa
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }
    
}

@Composable
actual fun rememberCameraManager(): CameraManager {
    val context = LocalContext.current
    val callbackState: MutableState<((String?) -> Unit)?> = remember { mutableStateOf(null) }
    val permissionCallbackState = remember { mutableStateOf<((Boolean) -> Unit)?>(null) }
    val currentPhotoUriState = remember { mutableStateOf<Uri?>(null) }
    
    // Launcher para solicitar permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionCallbackState.value?.invoke(isGranted)
        permissionCallbackState.value = null
    }
    
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val photoUri = currentPhotoUriState.value
            callbackState.value?.invoke(photoUri?.toString())
        } else {
            callbackState.value?.invoke(null)
        }
        callbackState.value = null
        currentPhotoUriState.value = null
    }
    
    val frontCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val photoUri = currentPhotoUriState.value
            callbackState.value?.invoke(photoUri?.toString())
        } else {
            callbackState.value?.invoke(null)
        }
        callbackState.value = null
        currentPhotoUriState.value = null
    }
    
    return remember {
        CameraManager(cameraLauncher, frontCameraLauncher, context, callbackState, permissionLauncher, permissionCallbackState, currentPhotoUriState)
    }
}

