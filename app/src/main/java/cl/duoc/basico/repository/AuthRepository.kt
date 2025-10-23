package cl.duoc.basico.repository

import cl.duoc.basico.model.UserDao
import cl.duoc.basico.model.UserEntity

class AuthRepository(private val userDao: UserDao) {

    suspend fun register(name: String, email: String, password: String): Result<Unit> {
        // Validaciones mínimas (la UI ya valida, aquí reforzamos)
        if (name.isBlank()) return Result.failure(IllegalArgumentException("Nombre requerido"))
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")))
            return Result.failure(IllegalArgumentException("Email inválido"))
        if (password.length < 6) return Result.failure(IllegalArgumentException("Mínimo 6 caracteres"))

        // Unicidad de email
        if (userDao.findByEmail(email) != null)
            return Result.failure(IllegalStateException("Email ya registrado"))

        val user = UserEntity(name = name, email = email, password = password) // demo: sin hash
        userDao.insert(user)
        return Result.success(Unit)
    }

    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.findByEmail(email) ?: return false
        return user.password == password //
    }
}
