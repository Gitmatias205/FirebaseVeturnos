package com.proyecto.firebaseveturnos.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Veturnos", "signInWithEmailAndPassword: Logueado correctamente")
                            home()
                        } else {
                            Log.d("Veturnos", "signInWithEmailAndPassword: ${task.exception?.message}")
                        }
                    }
            } catch (ex: Exception) {
                Log.d("Veturnos", "signInWithEmailAndPassword (excepción): ${ex.message}")
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            viewModelScope.launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser = task.result.user
                                if (firebaseUser != null) {
                                    val displayName = firebaseUser.email?.split("@")?.get(0)
                                    createUser(displayName)
                                    home()
                                } else {
                                    Log.d("Veturnos", "Usuario es null después del registro.")
                                }
                            } else {
                                Log.d("Veturnos", "createUserWithEmailAndPassword: ${task.exception?.message}")
                            }
                            _loading.value = false
                        }
                } catch (ex: Exception) {
                    Log.d("Veturnos", "createUserWithEmailAndPassword (excepción): ${ex.message}")
                    _loading.value = false
                }
            }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance().collection("users")
            .document(userId!!)
            .set(user)
            .addOnSuccessListener {
                Log.d("Veturnos", "Creado $userId")
            }
            .addOnFailureListener { e ->
                Log.d("Veturnos", "Error al crear usuario: ${e.message}")
            }
    }
}
