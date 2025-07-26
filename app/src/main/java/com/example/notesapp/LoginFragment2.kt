package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentLogin2Binding
import com.example.notesapp.databinding.FragmentRegisterBinding

class LoginFragment2 : Fragment() {
    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_mainFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}