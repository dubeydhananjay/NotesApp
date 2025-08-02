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
import com.example.notesapp.model.UserRequest
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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            val validationRes = validateUserInput()
            if(validationRes.first) {
                authViewModel.registerUser(getUserRequest())
            }
            else {
                binding.txtError.text = validationRes.second
            }

        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
        bindObservers()

    }

    private fun bindObservers() {
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

    private fun getUserRequest(): UserRequest {
        val username = binding.txtUsername.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(email, password, username)
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(userRequest.username, userRequest.email, userRequest.password, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}