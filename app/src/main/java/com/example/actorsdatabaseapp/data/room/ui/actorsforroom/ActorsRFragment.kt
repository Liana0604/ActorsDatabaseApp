package com.example.actorsdatabaseapp.data.room.ui.actorsforroom

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.data.room.ActorsViewModel
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies
import com.example.actorsdatabaseapp.data.room.ui.adapter.ActorsRAdapter
import com.example.actorsdatabaseapp.data.sqlite.models.Pet
import com.example.actorsdatabaseapp.databinding.FragmentActorsRBinding

class ActorsRFragment : Fragment() {
    private lateinit var binding: FragmentActorsRBinding
    private lateinit var actorsViewModel: ActorsViewModel
    private lateinit var actorsAdapter: ActorsRAdapter

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
        actorsAdapter = ActorsRAdapter { action, actor ->
            when (action) {
                ActorsRAdapter.ActionEnum.ACTION_DELETE -> {
                    deleteActor(actor)
                }
                ActorsRAdapter.ActionEnum.ACTION_ADD_MOVIE -> {
                    showAddMovieDialog(actor.id)
                }
            }
        }
        binding.actorsRecyclerView.visibility = View.VISIBLE
        binding.actorsRecyclerView.adapter = actorsAdapter

        actorsViewModel.getActorsData.observe(viewLifecycleOwner, Observer { actor ->
            actorsAdapter.setData(actor)
        })
    }

    private fun insertDataIntoDatabase() {
        val actorsName = binding.nameEditText.text.toString()
        val actorsSurname = binding.surnameEditText.text.toString()
        val actorsAge = binding.ageEditText.text.toString()
        val petName = binding.petNameEditText.text.toString()
        val petAge = binding.petAgeEditText.text.toString()
        val petIsSmartCheckBox = binding.isSmartCheckBox
        val pet = Pet(petName, petAge.toInt(), petIsSmartCheckBox.isChecked)
        val petList = ArrayList<Pet>()
        petList.add(pet)
        if (actorsName.isNotEmpty() && actorsSurname.isNotEmpty() && actorsAge.isNotEmpty()) {
            val actor = Actors(0, actorsName, actorsSurname, actorsAge.toInt(), petList)
            actorsViewModel.addActor(actor)
            Toast.makeText(requireContext(), "Record is saved", Toast.LENGTH_LONG).show()
            binding.nameEditText.text.clear()
            binding.surnameEditText.text.clear()
            binding.ageEditText.text.clear()
            binding.petNameEditText.text.clear()
            binding.petAgeEditText.text.clear()
        } else {
            Toast.makeText(
                requireContext(),
                "Name, Surname and age cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun deleteActor(actor: Actors) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete this actor")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            actorsViewModel.deleteActor(actor)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }

    private fun showAddMovieDialog(id: Int) {
        Dialog(requireActivity()).apply {
            setContentView(R.layout.dialog_add_movie)
            findViewById<TextView>(R.id.actorIdInDialogTV).setText(id.toString())
            findViewById<Button>(R.id.addMovieDialogButton).setOnClickListener {
               // val actorId = findViewById<TextView>(R.id.actorIdInDialogTV)
                val movieName = findViewById<EditText>(R.id.movieNameEditText).text.toString()
                val imdbRate = findViewById<EditText>(R.id.imdbRateEditText).text.toString()
                actorsViewModel.addMovie(
                    Movies(
                        actorId = id,
                        name = movieName,
                        imdbRate = imdbRate.toInt()
                    )
                )
                Toast.makeText(requireContext(), "New Movie Added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }.show()
    }

}