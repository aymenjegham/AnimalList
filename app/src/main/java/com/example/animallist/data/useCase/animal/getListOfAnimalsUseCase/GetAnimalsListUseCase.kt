package com.example.animallist.data.useCase.animal.getListOfAnimalsUseCase

import androidx.paging.PagingData
import com.example.animallist.data.model.Animal
import kotlinx.coroutines.flow.Flow


interface GetAnimalsListUseCase {

     operator fun invoke(): Flow<PagingData<Animal>>

}