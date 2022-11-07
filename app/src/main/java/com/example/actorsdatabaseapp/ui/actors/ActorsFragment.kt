package com.example.actorsdatabaseapp.ui.actors

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
import com.example.actorsdatabaseapp.MyApplication
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.databinding.FragmentActorsBinding
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsModel
import com.example.actorsdatabaseapp.data.sqlite.AppSQLiteHelper
import com.example.actorsdatabaseapp.data.sqlite.models.MoviesModel
import com.example.actorsdatabaseapp.ui.adapters.ActorsAdapter


class ActorsFragment : Fragment() {
    private lateinit var binding: FragmentActorsBinding
    private lateinit var actorsAdapter: ActorsAdapter
    private val sqLiteHelper: AppSQLiteHelper by lazy {
        (requireActivity().application as MyApplication).sqLiteHelper
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addActorButton.setOnClickListener {
            addActor()
        }
        setUpListOfActorsIntoRecyclerView()
    }

    private fun addActor() {
        val actorName = binding.nameEditText.text.toString()
        val actorSurname = binding.surnameEditText.text.toString()
        val actorAge = binding.ageEditText.text.toString()
//        val petName = binding.petNameEditText.text.toString()
//        val petAge = binding.petAgeEditText.text.toString()
//        val petIsSmart = binding.isSmartSwitcher
        if (actorName.isNotEmpty() && actorSurname.isNotEmpty() && actorAge.isNotEmpty()) {
            val actor = ActorsModel(
                name = actorName, surname = actorSurname, age = actorAge.toInt(),
                //    pets = arrayListOf()
            )
            val result = sqLiteHelper.addActor(actor)
            if (result > -1) {
                Toast.makeText(requireContext(), "Record is saved", Toast.LENGTH_LONG).show()
                binding.nameEditText.text.clear()
                binding.surnameEditText.text.clear()
                binding.ageEditText.text.clear()
//                binding.petNameEditText.text.clear()
//                binding.petAgeEditText.text.clear()

                setUpListOfActorsIntoRecyclerView()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Name, Surname and age cannot be blank",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun setUpListOfActorsIntoRecyclerView() {
        if (getItemsList().size > 0) {
            actorsAdapter = ActorsAdapter { action, actor ->
                when (action) {
                    ActorsAdapter.ActionEnum.ACTION_DELETE -> {
                        deleteActor(actor)
                    }
                    ActorsAdapter.ActionEnum.ACTION_ADD_MOVIE -> {
                        showAddMovieDialog(actor.id)
                    }
                }
                actorsAdapter.updateData(getItemsList())
            }
            actorsAdapter.actors = getItemsList()
            binding.actorsRecyclerView.visibility = View.VISIBLE
            binding.actorsRecyclerView.adapter = actorsAdapter
        } else {
            binding.actorsRecyclerView.visibility = View.GONE
        }
    }

    private fun getItemsList(): ArrayList<ActorsModel> {
        return sqLiteHelper.getActors()
    }

    private fun deleteActor(actor: ActorsModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete ${actor.id}")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            val result = sqLiteHelper.deleteactor(ActorsModel(actor.id, "", "", 0))
            if (result > -1) {
                Toast.makeText(requireContext(), "Record deleted successfully.", Toast.LENGTH_LONG)
                    .show()
                setUpListOfActorsIntoRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showAddMovieDialog(id: Int) {
        Dialog(requireActivity()).apply {
            setContentView(R.layout.dialog_add_movie)
            findViewById<TextView>(R.id.actorIdInDialogTV).setText(id.toString())

            findViewById<Button>(R.id.addMovieDialogButton).setOnClickListener {
                val actorId = findViewById<TextView>(R.id.actorIdInDialogTV)
                val movieName = findViewById<EditText>(R.id.movieNameEditText).text.toString()
                val imdbRate = findViewById<EditText>(R.id.imdbRateEditText).text.toString()
                sqLiteHelper.addMovie(
                    MoviesModel(
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
