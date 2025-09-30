package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project_2.NotificationHelper
import com.example.project_2.R
import com.example.project_2.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()

            if (name.isBlank() || email.isBlank() || phone.isBlank()) {
                Toast.makeText(requireContext(), "Bütün sahələri doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val testCode = "123456"
            NotificationHelper.showCodeNotification(requireContext(), testCode)

            // Növbəti ekrana keç
            findNavController().navigate(R.id.action_signUpFragment_to_verifyCodeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}