package com.example.actorsdatabaseapp.data.room.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actorsdatabaseapp.data.room.entities.Movies
import com.example.actorsdatabaseapp.databinding.ItemMovieLayoutBinding

class MoviesRAdapter(private val itemClickListener: (ActionEnum, Movies) -> Unit) :
    RecyclerView.Adapter<MoviesRAdapter.BaseViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context
    var movies: MutableList<Movies> = mutableListOf()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        MovieItemViewHolder(ItemMovieLayoutBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(movies[position])

    override fun getItemCount() = movies.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<Movies>) {
        this.movies.clear()
        this.movies.addAll(items)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Movies)
    }

    inner class MovieItemViewHolder(private var binding: ItemMovieLayoutBinding) :
        BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener(
                    ActionEnum.DELETE,
                    movies[adapterPosition]
                )
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(item: Movies) {
            item.let {
                val movieName = it.name
                val imbdrate = it.imdbRate
                val actorId = it.actorId
                binding.movieNameTextView.text = movieName
                binding.moviesIMDBRateTextView.text = imbdrate.toString()
                binding.actorIdTextView.text = actorId.toString()
            }
        }
    }

    enum class ActionEnum {
        DELETE
    }
}