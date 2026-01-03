package gas.control.project.presentation.navigation

import cafe.adriel.voyager.navigator.Navigator
import gas.control.project.presentation.screen.UserFormScreen

fun Navigator.pushUserForm(userId: String? = null) {
    push(UserFormScreen(userId))
}

// Esta función se puede usar para navegar desde cualquier lugar
fun Navigator.navigateToUserList() {
    popUntilRoot()
}

