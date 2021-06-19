package com.example.animallist.data.datasource.api


import com.example.animallist.data.model.AnimalResponse
import retrofit2.http.*


interface APIClient {


    @GET("species")
    suspend fun getAnimalsList(@Query("page") page : Int): AnimalResponse


}