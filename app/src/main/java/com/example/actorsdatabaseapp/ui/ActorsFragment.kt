package com.example.actorsdatabaseapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.actorsdatabaseapp.MyApplication
import com.example.actorsdatabaseapp.databinding.FragmentActorsBinding
import com.example.actorsdatabaseapp.modules.sqlite.ActorsModel
import com.example.actorsdatabaseapp.modules.sqlite.AppSQLiteHelper


class ActorsFragment : Fragment() {
    private lateinit var actorsViewModel: ActorsViewModel
    private lateinit var binding: FragmentActorsBinding
    private lateinit var actorsAdapter: ActorsAdapter
    private val sqLiteHelper: AppSQLiteHelper by lazy {
        (requireActivity().application as MyApplication).sqLiteHelper
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addActorButton.setOnClickListener {
            addActor(view)
        }
        setUpListOfActorsIntoRecyclerView()
    }

    private fun addActor(view: View) {
        val actorName = binding.nameEditText.text.toString()
        val actorSurname = binding.surnameEditText.text.toString()
        val actorAge = binding.ageEditText.text.toString()
        val movieId = binding.moviesIdEditText.text.toString()
        if (actorName.isNotEmpty() && actorSurname.isNotEmpty() && actorAge.isNotEmpty()) {
            val actor = ActorsModel(
                name = actorName,
                surname = actorSurname,
                age = actorAge.toInt(),
                movieId = movieId.toInt()
            )
            val result = sqLiteHelper.addActor(actor)
            if (result > -1) {
                Toast.makeText(requireContext(), "Record is saved", Toast.LENGTH_LONG).show()
                binding.nameEditText.text.clear()
                binding.surnameEditText.text.clear()
                binding.ageEditText.text.clear()
                binding.moviesIdEditText.text.clear()

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
        val sqliteHelper = AppSQLiteHelper(requireContext())
        return sqliteHelper.getActors()
    }

    private fun deleteActor(actor: ActorsModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete ${actor.id}")
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            val sqLiteHelper = AppSQLiteHelper(requireContext())
            val status = sqLiteHelper.deleteactor(ActorsModel(actor.id, "", "", 0, 0))
            if (status > -1) {
                Toast.makeText(requireContext(), "Record deleted successfully.", Toast.LENGTH_LONG)
                    .show()
                setUpListOfActorsIntoRecyclerView()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
