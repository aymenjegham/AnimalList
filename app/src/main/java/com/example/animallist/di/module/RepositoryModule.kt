package com.example.animallist.di.module

import com.example.animallist.data.repository.animal.AnimalRepository
import com.example.animallist.data.repository.animal.AnimalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindDeviceRepository(repository: AnimalRepositoryImpl): AnimalRepository

}