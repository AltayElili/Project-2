package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project_2.R
import com.example.project_2.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.getValue

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val email = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            var hasError = false
            if (email.isBlank()) {
                binding.edtUsername.error = "Email tələb olunur"; hasError = true
            }
            if (password.isBlank()) {
                binding.edtPassword.error = "Şifrə tələb olunur"; hasError = true
            }
            if (hasError) return@setOnClickListener

            signInWithFirebase(email, password)
        }
        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
            firebaseAuth.currentUser?.let {
                findNavController().navigate(R.id.action_loginFragment_to_menuHome)
            }
        }


        private fun signInWithFirebase(email: String, password: String) {
            lifecycleScope.launch {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                    Toast.makeText(requireContext(), "Giriş uğurludur", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_menuHome)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message ?: "Giriş alınmadı", Toast.LENGTH_LONG).show()
                }
            }
        }
}
