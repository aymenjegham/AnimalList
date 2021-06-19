package com.example.animallist.data.repository.animal

import androidx.paging.PagingData
import com.example.animallist.data.model.Animal
import kotlinx.coroutines.flow.Flow


interface AnimalRepository {

     fun getAnimalsList(): Flow<PagingData<Animal>>


}