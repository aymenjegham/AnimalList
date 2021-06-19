package com.example.animallist.di.module


import com.example.animallist.data.useCase.animal.getListOfAnimalsUseCase.GetAnimalsListUseCase
import com.example.animallist.data.useCase.animal.getListOfAnimalsUseCase.GetAnimalsListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetDeviceTokenUseCase(useCase: GetAnimalsListUseCaseImpl): GetAnimalsListUseCase

}
