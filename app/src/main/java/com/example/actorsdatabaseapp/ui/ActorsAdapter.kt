package com.example.actorsdatabaseapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actorsdatabaseapp.databinding.ItemActorLayoutBinding
import com.example.actorsdatabaseapp.modules.sqlite.ActorsModel

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
    ): ActorsAdapter.BaseViewHolder =
        ActorItemViewHolder(ItemActorLayoutBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: ActorsAdapter.BaseViewHolder, position: Int) {
        holder.bind(actors[position])
    }

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
            binding.root.setOnClickListener {
                itemClickListener(
                    ActionEnum.ACTION_DELETE,
                    actors[adapterPosition]
                )
            }
        }

        override fun bind(item: ActorsModel) {
            item.let {
                binding.actorsNameTextView.text = item.name
                binding.actorsSurnameTextView.text = item.surname
                binding.actorsAgeTextView.text = item.age.toString()
            }
        }
    }

    enum class ActionEnum {
        ACTION_DELETE
    }
}