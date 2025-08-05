package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentNoteBinding
import com.example.notesapp.model.NotesRequest
import com.example.notesapp.model.NotesResponse
import com.example.notesapp.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var note: NotesResponse? = null
    private val notesViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindObservers()
        bindHandlers()
    }

    private fun setInitialData() {
        val jsonNote: String? = arguments?.getString("note")
        if(jsonNote != null) {
            note = Gson().fromJson<NotesResponse>(jsonNote, NotesResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.desc)
            }
        }
        else {
            binding.addEditText.text = resources.getString(R.string.add_note)
        }
    }

    private fun bindObservers() {
        notesViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Error<*> -> {

                }
                is NetworkResult.Loading<*> -> {

                }
                is NetworkResult.Success<*> -> findNavController().popBackStack()
            }
        }
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let { notesViewModel.deleteNote(it._id) }
        }
        binding.apply {
            btnSubmit.setOnClickListener {
                val title = txtTitle.text.toString()
                val desc = txtDescription.text.toString()
                val noteRequest = NotesRequest(title, desc)
                if(note == null) {
                    notesViewModel.createNode(noteRequest)
                }
                else {
                    notesViewModel.updateNote(note!!._id, noteRequest)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}