package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.NavOptions
import com.example.project_2.R
import com.example.project_2.databinding.FragmentCustomerBinding
import com.google.firebase.auth.FirebaseAuth

class CustomerFragment : Fragment() {

    private lateinit var binding: FragmentCustomerBinding
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = firebaseAuth.currentUser
        if (user == null) {
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build()
            )
            return
        }
        val displayName = user.displayName
        val email = user.email

        binding.tvUsername.text = when {
            !displayName.isNullOrBlank() -> displayName
            !email.isNullOrBlank() -> email.substringBefore("@")
            else -> "User"
        }
        binding.tvEmail.text = email ?: "Email not available"

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(requireContext(), "Logout successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build()
            )
        }
    }
}
