package io.github.staakk.randomcity.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.staakk.randomcity.data.model.City
import io.github.staakk.randomcity.databinding.ItemCityBinding
import java.time.format.DateTimeFormatter

class CitiesAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(city: City)
    }

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yy, HH:mm:ss")

    var items: List<City> = listOf()
        set(value) {
            DiffUtil.calculateDiff(DiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CityViewHolder(
        private val viewBinding: ItemCityBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(city: City) = with(viewBinding) {
            root.setOnClickListener { onItemClickListener.onItemClick(city) }

            name.text = city.name
            createdAt.text = city.createdAt.format(dateTimeFormatter)
        }
    }

    class DiffCallback(
        private val oldItems: List<City>,
        private val newItems: List<City>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    }
}