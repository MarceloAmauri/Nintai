package cl.duoc.basico.model

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit()
            .putBoolean("isLoggedIn", isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
