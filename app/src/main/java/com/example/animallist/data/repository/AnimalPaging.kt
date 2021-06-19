package com.example.animallist.data.repository

import androidx.paging.PagingSource
import com.example.animallist.data.datasource.api.APIClient
import com.example.animallist.data.model.Animal

class AnimalPaging(
    val apiClient: APIClient
) : PagingSource<Int, Animal>() {


    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Animal> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiClient.getAnimalsList(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}