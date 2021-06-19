package com.example.animallist.ui.main.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animallist.data.model.Animal
import com.example.animallist.global.helpers.DebugLog


class AnimalAdapter(
    private val selectAction: (Int) -> Unit

) : PagingDataAdapter<Animal, AnimalItemHolder>(AnimalComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnimalItemHolder.create(parent, selectAction)


    override fun onBindViewHolder(itemHolder: AnimalItemHolder, position: Int) {
        return itemHolder.bind(getItem(position)!!)
    }

    fun changeColorState(position: Int) {
        getItem(position)?.let {
            it.state = !it.state
        }
        notifyItemChanged(position)
    }


    object AnimalComparator : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {

            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }


}
