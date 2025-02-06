package edu.victorlamas.demo05.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import edu.victorlamas.demo05.R
import edu.victorlamas.demo05.databinding.ItemShowBinding
import edu.victorlamas.demo05.model.shows.ShowsItem

/**
 * Clase ShowsAdapter.kt
 * Adaptador para la lista de series en MainActivity.
 * @author Víctor Lamas
 *
 * @param onClickShowsItem Evento al hacer click en una serie.
 * @param onClickShowFavorite Evento al hacer click en el icono de favorita.
 * @param onClickShowWatch Evento al hacer click en el icono de vista.
 */
class ShowsAdapter(
    val onClickShowsItem: (idShow: Int) -> Unit,
    val onClickShowFavorite: (show: ShowsItem) -> Unit,
    val onClickShowWatch: (show: ShowsItem) -> Unit
) : ListAdapter<ShowsItem, ShowsAdapter.ShowsViewHolder>(ShowsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        return ShowsViewHolder(
            ItemShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShowsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bind = ItemShowBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(show: ShowsItem) {
            bind.tvName.text = show.name.uppercase()
            if (show.network != null) {
                bind.tvProducer.text = show.network.name
            }

            bind.tvAverage.text = show.rating.average.toString()
            bind.tvGender.text = show.genres.joinToString(", ")

            Glide.with(itemView.context)
                .load(show.image.medium)
                .transform(FitCenter(), RoundedCorners(8))
                .into(bind.ivIcon)

            itemView.setOnClickListener {
                onClickShowsItem(show.id)
            }

            bind.ivFav.setOnClickListener {
                onClickShowFavorite(show)
                notifyItemChanged(adapterPosition)
            }

            bind.ivWatched.setOnClickListener {
                onClickShowWatch(show)
                notifyItemChanged(adapterPosition)
            }

            bind.ivFav.setImageState(
                intArrayOf(R.attr.state_on),
                show.favorite
            )

            bind.ivWatched.setImageState(
                intArrayOf(R.attr.state_on),
                show.watched
            )
        }
    }
}

/**
 * Class ShowsAdapter.kt
 * Compara dos items por su ID.
 * @author Víctor Lamas
 */
class ShowsDiffCallback : DiffUtil.ItemCallback<ShowsItem>() {
    override fun areItemsTheSame(oldItem: ShowsItem, newItem: ShowsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowsItem, newItem: ShowsItem): Boolean {
        return oldItem == newItem
    }
}