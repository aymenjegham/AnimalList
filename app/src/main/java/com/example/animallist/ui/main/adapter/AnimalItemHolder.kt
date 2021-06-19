package com.example.animallist.ui.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animallist.data.model.Animal
import com.example.animallist.databinding.ItemAnimalBinding

class AnimalItemHolder private constructor(
    private val binding: ItemAnimalBinding,
    private val selectAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setSelectAction { selectAction(absoluteAdapterPosition) }
    }

    fun bind(item: Animal) {
        binding.animal = item
    }


    companion object {
        fun create(
            parent: ViewGroup,
            selectAction: (Int) -> Unit
        ) =
            LayoutInflater.from(parent.context)
                .let { ItemAnimalBinding.inflate(it, parent, false) }
                .let { AnimalItemHolder(it, selectAction) }
    }
}