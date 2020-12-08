package com.group7.android.todolist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group7.android.todolist.Broker

class PageViewModel : ViewModel() {
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        if(it==1) "To-Do" else "Finished"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}