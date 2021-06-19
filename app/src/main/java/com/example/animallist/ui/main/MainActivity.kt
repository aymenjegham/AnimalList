package com.example.animallist.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.animallist.R
import com.example.animallist.databinding.ActivityMainBinding
import com.example.animallist.ui.base.BaseActivity
import com.example.animallist.ui.main.adapter.AnimalAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModels()

    private lateinit var animalAdapter: AnimalAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .also(::bind)
    }

    private fun bind(binding: ActivityMainBinding) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        registerMainObserver()
        animalAdapter = AnimalAdapter(selectTile)
        binding.adapter = animalAdapter
        registerListOfAnimalsObservers()

    }

    private fun registerListOfAnimalsObservers() {
        viewModel.animalsList.observe(this, Observer {
            lifecycleScope.launch {
                animalAdapter.submitData(it)
            }
        })
    }

    private val selectTile: (Int) -> Unit = {

        animalAdapter.snapshot()[it]?.let {

        }
    }

    private fun registerMainObserver() {
        registerBaseObservers(viewModel)
    }
}