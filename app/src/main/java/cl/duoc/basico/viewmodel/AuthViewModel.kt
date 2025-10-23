package cl.duoc.basico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.AppDatabase
import cl.duoc.basico.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: AuthRepository by lazy {
        val db = AppDatabase.get(app)
        AuthRepository(db.userDao())
    }

    //  Ahora son estados observables por Compose
    var error: String? by mutableStateOf(null)
        private set

    var submitting: Boolean by mutableStateOf(false)
        private set

    fun register(name: String, email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            submitting = true
            error = null
            val r = repo.register(name, email, pass)
            if (r.isSuccess) onSuccess() else error = r.exceptionOrNull()?.message
            submitting = false
        }
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            submitting = true
            error = null
            val ok = repo.login(email, pass)
            if (ok) onSuccess() else error = "Credenciales inválidas"
            submitting = false
        }
    }
}
