package gas.control.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import gas.control.project.data.local.DatabaseDriverFactory
import gas.control.project.data.remote.HttpClientFactory
import gas.control.project.presentation.di.presentationModule
import gas.control.project.presentation.screen.LoginScreen
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val httpClientFactory = remember { getHttpClientFactory() }
            val databaseDriverFactory = getDatabaseDriverFactory()
            
            val baseUrl = remember { getBaseUrl() }
            
            KoinApplication(application = {
                modules(
                    presentationModule(
                        httpClientFactory = httpClientFactory,
                        databaseDriverFactory = databaseDriverFactory,
                        baseUrl = baseUrl
                    )
                )
            }) {
                Navigator(LoginScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}

expect fun getHttpClientFactory(): HttpClientFactory
@Composable
expect fun getDatabaseDriverFactory(): DatabaseDriverFactory
expect fun getBaseUrl(): String
