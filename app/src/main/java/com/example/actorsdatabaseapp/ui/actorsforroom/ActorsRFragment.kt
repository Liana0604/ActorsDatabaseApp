package com.example.actorsdatabaseapp.ui.actorsforroom

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.data.room.ActorsViewModel
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsModel
import com.example.actorsdatabaseapp.data.sqlite.models.MoviesModel
import com.example.actorsdatabaseapp.data.sqlite.models.Pet
import com.example.actorsdatabaseapp.databinding.FragmentActorsRBinding
import com.example.actorsdatabaseapp.ui.adapters.ActorsAdapter
import com.example.actorsdatabaseapp.ui.adapters.ActorsRAdapter
import kotlinx.coroutines.supervisorScope

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
                    deletActor(actor)
                }
                ActorsRAdapter.ActionEnum.ACTION_ADD_MOVIE -> {
                    //  showAddMovieDialog(id)
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
        val petIsSmart = binding.isSmartSwitcher
//        petIsSmart.setOnCheckedChangeListener { compoundButton, b ->
//            if ()
//        }
        val pet = Pet(petName, petAge, petIsSmart)
        val petList = ArrayList<Pet>()
        petList.add(pet)

        if (actorsName.isNotEmpty() && actorsSurname.isNotEmpty() && actorsAge.isNotEmpty()) {
            val actor = Actors(0, actorsName, actorsSurname, actorsAge.toInt(), petList)
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

    private fun deletActor(actor: Actors) {
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
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.setCancelable(false)
//        alertDialog.show()
    }

    private fun showAddMovieDialog(id: Int) {
        Dialog(requireActivity()).apply {
            setContentView(R.layout.dialog_add_movie)
            findViewById<TextView>(R.id.actorIdInDialogTV).setText(id.toString())

            findViewById<Button>(R.id.addMovieDialogButton).setOnClickListener {
                val actorId = findViewById<TextView>(R.id.actorIdInDialogTV)
                val movieName = findViewById<EditText>(R.id.movieNameEditText).text.toString()
                val imdbRate = findViewById<EditText>(R.id.imdbRateEditText).text.toString()

                Toast.makeText(requireContext(), "New Movie Added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }.show()
    }

}