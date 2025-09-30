package com.example.project_2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project_2.R
import com.example.project_2.databinding.FragmentMenuProfileBinding
import com.example.project_2.service.AuthService
import com.example.project_2.service.RetrofitInstance
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuProfile : Fragment() {
    private lateinit var binding: FragmentMenuProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = Firebase.auth
        getUserDetails()
        binding.btnLogout.setOnClickListener {
            logOut()
        }

    }
    private fun logOut(){
        firebaseAuth.signOut()
        findNavController().navigate(R.id.action_menuProfile_to_loginFragment)
        Toast.makeText(requireContext(), "Logout successful", Toast.LENGTH_SHORT).show()
    }
    private fun getUserDetails() {
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val authService = RetrofitInstance.api.create(AuthService::class.java)
                val response = authService.getUserDetails()

                val user = response.body()
                if (response.isSuccessful) {
                    if (user != null) {
                        withContext(Dispatchers.Main) {
                            binding.tvUsername.text = user.username ?: "Username not available"
                            binding.tvEmail.text = user.email ?: "Email not available"
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "User data is null",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to retrieve user info",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileFragment", "Error: ${e.message}")
            }
        }
    }
}
