package com.example.actorsdatabaseapp.ui.actorsforroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.actorsdatabaseapp.data.room.ActorsViewModel
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.databinding.FragmentActorsRBinding

class ActorsRFragment : Fragment() {
    private lateinit var binding: FragmentActorsRBinding
    private lateinit var actorsViewModel: ActorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorsRBinding.inflate(layoutInflater)
        actorsViewModel = ViewModelProvider(this)[ActorsViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addActorButton.setOnClickListener {
            insertDataIntoDatabase()
        }
    }

    private fun insertDataIntoDatabase() {
        val actorsName = binding.nameEditText.text.toString()
        val actorsSurname = binding.surnameEditText.text.toString()
        val actorsAge = binding.ageEditText.text.toString()
        if (actorsName.isNotEmpty() && actorsSurname.isNotEmpty() && actorsAge.isNotEmpty()) {
            val actor = Actors(actorsName, actorsSurname, actorsAge.toInt())
            actorsViewModel.addActor(actor)
            Toast.makeText(requireContext(), "Record is saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Name, Surname and age cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}