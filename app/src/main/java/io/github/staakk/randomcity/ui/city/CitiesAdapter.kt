package io.github.staakk.randomcity.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.staakk.randomcity.data.City
import io.github.staakk.randomcity.databinding.ItemCityBinding
import java.time.format.DateTimeFormatter

class CitiesAdapter(
    private val onItemClickListener: (City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yy, HH:mm:ss")

    var items: List<City> = listOf()
        set(value) {
            notifyDataSetChanged()
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
            root.setOnClickListener { onItemClickListener(city) }

            name.text = city.name
            createdAt.text = city.createdAt.format(dateTimeFormatter)
        }
    }
}