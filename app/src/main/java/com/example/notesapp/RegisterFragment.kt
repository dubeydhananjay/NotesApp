package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentRegisterBinding
import com.example.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            //findNavController().navigate((R.id.action_registerFragment_to_mainFragment))
        }

        binding.btnLogin.setOnClickListener {
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Error<*> -> {
                    binding.txtError.text = it.message
                }

                is NetworkResult.Loading<*> -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Success<*> -> {
                    //token
                    findNavController().navigate((R.id.action_registerFragment_to_mainFragment))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}