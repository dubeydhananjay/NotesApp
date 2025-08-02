package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentLogin2Binding
import com.example.notesapp.databinding.FragmentRegisterBinding
import com.example.notesapp.model.UserRequest
import com.example.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment2 : Fragment() {
    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val validateUser = validateUserInput()
            if(validateUser.first) {
                authViewModel.loginUser(getUserRequest())
            }
            else {
                binding.txtError.text = validateUser.second
            }
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
        bindObservers()

    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when(it) {
                is NetworkResult.Error<*> -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading<*> -> {
                    binding.progressBar.isVisible = true
                }
                is NetworkResult.Success<*> -> {
                    findNavController().navigate(R.id.action_loginFragment2_to_mainFragment)
                }
            }
        }
    }

    private fun getUserRequest(): UserRequest {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(email, password, "")
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials("", userRequest.email, userRequest.password, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}