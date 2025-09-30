package com.example.project_2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project_2.ui.LoginViewModel
import com.example.project_2.R
import com.example.project_2.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { success ->
            if (success) {
                Log.d("LoginFragment", "Login successful")
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Log.d("LoginFragment", "Login failed")
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.messageResultLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.btnSave.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            if (username.isBlank()) binding.edtUsername.error = "You must fill Username"
            if (password.isBlank()) binding.edtPassword.error = "You must fill Password"
            lifecycleScope.launch {
                viewModel.loginUser(username, password)
            }
        }
    }
}