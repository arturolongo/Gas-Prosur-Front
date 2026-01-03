package gas.control.project.presentation.di

import gas.control.project.data.DataModule
import gas.control.project.data.local.DatabaseDriverFactory
import gas.control.project.data.remote.HttpClientFactory
import gas.control.project.data.repository.UserRepositoryImpl
import gas.control.project.domain.repository.CatalogosRepository
import gas.control.project.domain.repository.ConductoresRepository
import gas.control.project.domain.repository.UserRepository
import gas.control.project.domain.usecase.CreateUserUseCase
import gas.control.project.domain.usecase.DeleteUserUseCase
import gas.control.project.domain.usecase.GetAllUsersUseCase
import gas.control.project.domain.usecase.GetConductorByIdUseCase
import gas.control.project.domain.usecase.GetEstadoByIdUseCase
import gas.control.project.domain.repository.AuthRepository
import gas.control.project.domain.usecase.GetEstadosUseCase
import gas.control.project.domain.usecase.GetUserByIdUseCase
import gas.control.project.domain.usecase.IsAuthenticatedUseCase
import gas.control.project.domain.usecase.LoginUseCase
import gas.control.project.domain.usecase.LogoutUseCase
import gas.control.project.domain.usecase.SyncUsersUseCase
import gas.control.project.domain.usecase.UpdateUserUseCase
import gas.control.project.presentation.screenmodel.ConductorDashboardScreenModel
import gas.control.project.presentation.screenmodel.ConductorOnboardingScreenModel
import gas.control.project.presentation.screenmodel.EstadosListScreenModel
import gas.control.project.presentation.screenmodel.LoginScreenModel
import gas.control.project.presentation.screenmodel.UserFormScreenModel
import gas.control.project.presentation.screenmodel.UserListScreenModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun presentationModule(
    httpClientFactory: HttpClientFactory,
    databaseDriverFactory: DatabaseDriverFactory,
    baseUrl: String = "https://api.example.com"
) = module {
    
    // Base URL para construir URLs de imágenes
    single<String>(named("baseUrl")) { baseUrl }
    
    // Data Module
    single<DataModule> {
        DataModule(httpClientFactory, databaseDriverFactory, baseUrl)
    }
    
    // Repositories
    single<AuthRepository> {
        get<DataModule>().authRepository
    }
    
    single<UserRepository> {
        get<DataModule>().userRepository
    }
    
    single<CatalogosRepository> {
        get<DataModule>().catalogosRepository
    }
    
    single<ConductoresRepository> {
        get<DataModule>().conductoresRepository
    }
    
    // Use Cases - Auth
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { IsAuthenticatedUseCase(get()) }
    
    // Use Cases - User (temporal)
    factory { GetAllUsersUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
    factory { CreateUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { SyncUsersUseCase(get()) }
    
    // Use Cases - Catálogos
    factory { GetEstadosUseCase(get()) }
    factory { GetEstadoByIdUseCase(get()) }
    
    // Use Cases - Conductores
    factory { GetConductorByIdUseCase(get()) }
    
    // ScreenModels
    factory { LoginScreenModel(get(), get()) }
    factory { (userId: String?) -> UserFormScreenModel(get(), get(), get(), userId) }
    factory { UserListScreenModel(get(), get(), get()) }
    factory { EstadosListScreenModel(get()) }
    factory { (nombre: String, idUsuario: Int) -> ConductorOnboardingScreenModel(nombre, idUsuario, get()) }
    factory { (conductorId: Int) -> ConductorDashboardScreenModel(conductorId, get()) }
}

