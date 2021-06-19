package com.example.animallist.data.useCase.animal.getListOfAnimalsUseCase


import androidx.paging.PagingData
import androidx.paging.map
import com.example.animallist.data.model.Animal
import com.example.animallist.data.repository.animal.AnimalRepository
import com.example.animallist.global.helpers.DebugLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetAnimalsListUseCaseImpl @Inject constructor(
    private val animalRepository: AnimalRepository
) :
    GetAnimalsListUseCase {

    override  fun invoke(): Flow<PagingData<Animal>> {

        return animalRepository.getAnimalsList()

    }
}