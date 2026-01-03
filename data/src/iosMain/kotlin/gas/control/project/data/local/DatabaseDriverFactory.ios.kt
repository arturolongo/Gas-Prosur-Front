package gas.control.project.data.local

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = GasControlDatabase.Schema,
            name = "gas_control.db"
        )
    }
}

