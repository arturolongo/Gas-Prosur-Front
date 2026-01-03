package gas.control.project.domain.model

import kotlinx.datetime.Instant

data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null
)
