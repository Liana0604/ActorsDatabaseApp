package com.example.actorsdatabaseapp.data.room.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.databinding.ItemActorLayoutBinding

class ActorsRAdapter(private val itemClickListener: (ActionEnum, Actors) -> Unit) :
    RecyclerView.Adapter<ActorsRAdapter.BaseViewHolder>() {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context
    var actors: MutableList<Actors> = mutableListOf()

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
    fun setData(actor: MutableList<Actors>) {
        this.actors = actor
        notifyDataSetChanged()
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Actors)
    }

    inner class ActorItemViewHolder(private var binding: ItemActorLayoutBinding) :
        BaseViewHolder(binding.root) {
        init {
            binding.deleteIconButton.setOnClickListener {
                itemClickListener(
                    ActionEnum.ACTION_DELETE,
                    actors[adapterPosition]
                )
            }
            binding.addIconButton.setOnClickListener {
                itemClickListener(
                    ActionEnum.ACTION_ADD_MOVIE, actors[adapterPosition]
                )
            }
        }

        override fun bind(item: Actors) {
            item.let {
                item.pets?.forEach {
                    binding.petsNameTextView.text = it.name
                    binding.petsAgeTextView.text = it.age.toString()
                    binding.petIsSmartTextView.text = it.isSmart.toString()
                }
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