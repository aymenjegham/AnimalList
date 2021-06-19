package com.example.animallist.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.animallist.data.model.Animal
import com.example.animallist.data.useCase.animal.getListOfAnimalsUseCase.GetAnimalsListUseCase
import com.example.animallist.global.helpers.SingleEventLiveDataEvent
import com.example.animallist.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers



class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val getAnimalsListUseCase: GetAnimalsListUseCase
) : BaseViewModel(app) {

    private val fetch = SingleEventLiveDataEvent<Boolean>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val animalFlow =
        getAnimalsListUseCase()
            .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    val animalsList = Transformations.switchMap(fetch){
       animalFlow
    }

    init {
        fetch.postValue(true)
    }
    fun refresh() {
        fetch.postValue(true)
        _isLoading.postValue(false)
    }



    fun selectTile(index: Animal){
        animalsList.observeForever {

        }
    }


}