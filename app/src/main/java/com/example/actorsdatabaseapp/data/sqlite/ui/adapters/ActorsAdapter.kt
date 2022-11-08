package com.example.actorsdatabaseapp.data.sqlite.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsModel
import com.example.actorsdatabaseapp.databinding.ItemActorLayoutBinding

class ActorsAdapter(private val itemClickListener: (ActionEnum, ActorsModel) -> Unit) :
    RecyclerView.Adapter<ActorsAdapter.BaseViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context
    var actors: MutableList<ActorsModel> = mutableListOf()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder =
        ActorItemViewHolder(ItemActorLayoutBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(actors[position])

    override fun getItemCount() = actors.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<ActorsModel>) {
        this.actors.clear()
        this.actors.addAll(items)
        notifyDataSetChanged()
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: ActorsModel)
    }

    inner class ActorItemViewHolder(private var binding: ItemActorLayoutBinding) :
        BaseViewHolder(binding.root) {
        init {
            binding.deleteIconButton.setOnClickListener {
                itemClickListener(ActionEnum.ACTION_DELETE, actors[adapterPosition])
            }
            binding.addIconButton.setOnClickListener {
                itemClickListener(ActionEnum.ACTION_ADD_MOVIE, actors[adapterPosition])
            }
        }

        override fun bind(item: ActorsModel) {
            item.let {
                binding.petsNameTextView.text = item.pets.toString()
                binding.actorsNameTextView.text = item.name
                binding.actorsSurnameTextView.text = item.surname
                binding.actorsAgeTextView.text = item.age.toString()

            }
        }
    }

    enum class ActionEnum {
        ACTION_DELETE,
        ACTION_ADD_MOVIE
    }
}