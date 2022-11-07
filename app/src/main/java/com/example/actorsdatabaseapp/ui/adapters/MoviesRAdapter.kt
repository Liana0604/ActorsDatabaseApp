package com.example.actorsdatabaseapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.databinding.ItemMovieLayoutBinding

class MoviesRAdapter(private val itemClickListener: (ActionEnum, ActorWithMovies) -> Unit) :
    RecyclerView.Adapter<MoviesRAdapter.BaseViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context
    var movies: MutableList<ActorWithMovies> = mutableListOf()

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
    fun updateData(items: MutableList<ActorWithMovies>) {
        this.movies.clear()
        this.movies.addAll(items)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: ActorWithMovies)
    }

    inner class MovieItemViewHolder(private var binding: ItemMovieLayoutBinding) :
        MoviesRAdapter.BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener(
                    ActionEnum.DELETE,
                    movies[adapterPosition]
                )
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(item: ActorWithMovies) {
            item.let {
//                binding.actorIdTextView.text = item.actorId.toString()
//                binding.actorNameTextView.text = item.actorName
//                binding.movieNameTextView.text = item.movieName
//                binding.moviesIMDBRateTextView.text = item.imdbRate.toString()

//                binding.actorIdTextView.text = "actor id: ${item.actorId}"
//                binding.actorNameTextView.text = "actor name: ${item.actorName}"
//                binding.movieNameTextView.text = "movie name: ${item.movieName}"
//                binding.moviesIMDBRateTextView.text = "imdb ratew: ${item.imdbRate}"

            }
        }
    }

    enum class ActionEnum {
        DELETE
    }
}