package gas.control.project.domain.usecase

import gas.control.project.domain.model.AutoDashboard
import gas.control.project.domain.repository.AutosRepository

class GetAutosDashboardUseCase(
    private val autosRepository: AutosRepository
) {
    suspend operator fun invoke(): Result<List<AutoDashboard>> {
        return autosRepository.getDashboard()
    }
}
