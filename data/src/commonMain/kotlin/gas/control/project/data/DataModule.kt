package gas.control.project.data

import gas.control.project.data.local.DatabaseDriverFactory
import gas.control.project.data.local.GasControlDatabase
import gas.control.project.data.local.UserLocalDataSource
import gas.control.project.data.local.UserLocalDataSourceImpl
import gas.control.project.data.remote.HttpClientFactory
import gas.control.project.data.remote.UserRemoteDataSource
import gas.control.project.data.remote.UserRemoteDataSourceImpl
import gas.control.project.data.remote.api.AuthApiService
import gas.control.project.data.remote.api.AuthApiServiceImpl
import gas.control.project.data.remote.api.AutosApiService
import gas.control.project.data.remote.api.AutosApiServiceImpl
import gas.control.project.data.remote.api.CatalogosApiService
import gas.control.project.data.remote.api.CatalogosApiServiceImpl
import gas.control.project.data.remote.api.ConductoresApiService
import gas.control.project.data.remote.api.ConductoresApiServiceImpl
import gas.control.project.data.remote.api.UserApiService
import gas.control.project.data.remote.api.UserApiServiceImpl
import gas.control.project.data.remote.api.ViajesApiService
import gas.control.project.data.remote.api.ViajesApiServiceImpl
import gas.control.project.data.repository.AuthRepositoryImpl
import gas.control.project.data.repository.AutosRepositoryImpl
import gas.control.project.data.repository.CatalogosRepositoryImpl
import gas.control.project.data.repository.ConductoresRepositoryImpl
import gas.control.project.data.repository.UserRepositoryImpl
import gas.control.project.data.repository.ViajesRepositoryImpl
import gas.control.project.data.storage.TokenStorage
import gas.control.project.domain.repository.AuthRepository
import gas.control.project.domain.repository.AutosRepository
import gas.control.project.domain.repository.CatalogosRepository
import gas.control.project.domain.repository.ConductoresRepository
import gas.control.project.domain.repository.UserRepository
import gas.control.project.domain.repository.ViajesRepository

class DataModule(
    private val httpClientFactory: HttpClientFactory,
    private val databaseDriverFactory: DatabaseDriverFactory,
    private val baseUrl: String = "http://localhost:5057/api"
) {
    
    private val tokenStorage by lazy {
        TokenStorage()
    }
    
    private val httpClient by lazy {
        httpClientFactory.create(tokenStorage, baseUrl)
    }
    
    private val database by lazy {
        GasControlDatabase(databaseDriverFactory.createDriver())
    }
    
    // Auth
    private val authApiService: AuthApiService by lazy {
        AuthApiServiceImpl(httpClient, baseUrl, tokenStorage)
    }
    
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService)
    }
    
    // Autos
    private val autosApiService: AutosApiService by lazy {
        AutosApiServiceImpl(httpClient, baseUrl, tokenStorage)
    }
    
    val autosRepository: AutosRepository by lazy {
        AutosRepositoryImpl(autosApiService)
    }
    
    // Conductores
    private val conductoresApiService: ConductoresApiService by lazy {
        ConductoresApiServiceImpl(httpClient, baseUrl, tokenStorage)
    }
    
    val conductoresRepository: ConductoresRepository by lazy {
        ConductoresRepositoryImpl(conductoresApiService)
    }
    
    // Viajes
    private val viajesApiService: ViajesApiService by lazy {
        ViajesApiServiceImpl(httpClient, baseUrl, tokenStorage)
    }
    
    val viajesRepository: ViajesRepository by lazy {
        ViajesRepositoryImpl(viajesApiService)
    }
    
    // Catálogos
    private val catalogosApiService: CatalogosApiService by lazy {
        CatalogosApiServiceImpl(httpClient, baseUrl, tokenStorage)
    }
    
    val catalogosRepository: CatalogosRepository by lazy {
        CatalogosRepositoryImpl(catalogosApiService)
    }
    
    // User (temporal, se eliminará después)
    private val userApiService: UserApiService by lazy {
        UserApiServiceImpl(httpClient, baseUrl)
    }
    
    private val userRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSourceImpl(userApiService)
    }
    
    private val userLocalDataSource: UserLocalDataSource by lazy {
        UserLocalDataSourceImpl(database)
    }
    
    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }
}

