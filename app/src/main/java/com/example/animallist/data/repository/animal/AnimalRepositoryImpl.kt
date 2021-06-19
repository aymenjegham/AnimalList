package com.example.animallist.data.repository.animal

import android.content.Context
import androidx.paging.*
import com.example.animallist.data.datasource.api.APIClient
import com.example.animallist.data.model.Animal
import com.example.animallist.data.repository.AnimalPaging

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimalRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: APIClient
) : AnimalRepository {


    override  fun getAnimalsList(): Flow<PagingData<Animal>> =
        Pager(PagingConfig(pageSize = 10)) {
            AnimalPaging(apiClient)
        }.flow


}