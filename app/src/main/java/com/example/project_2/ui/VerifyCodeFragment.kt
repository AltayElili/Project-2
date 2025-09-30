package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project_2.R
import com.example.project_2.databinding.FragmentVerifyCodeBinding

class VerifyCodeFragment : Fragment() {

    private var _binding: FragmentVerifyCodeBinding? = null
    private val binding get() = _binding!!

    private val correctCode = "123456" // hardcoded test kod

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVerify.setOnClickListener {
            val enteredCode = binding.edtCode.text.toString().trim()
            val correctCode = "123456"

            if (enteredCode == correctCode) {
                Toast.makeText(requireContext(), "Kod düzgün daxil edildi", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_verifyCodeFragment_to_menuHome)
            } else {
                Toast.makeText(requireContext(), "Kod səhvdir!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}