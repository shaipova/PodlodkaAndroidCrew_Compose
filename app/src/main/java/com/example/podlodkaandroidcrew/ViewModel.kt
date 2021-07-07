package com.example.podlodkaandroidcrew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    private var _search = MutableLiveData<String>()
    val search: LiveData<String>
        get() = _search

    fun setSearch(searchText: String) {
        _search.postValue(searchText)
    }

//    private var _fav = MutableLiveData<Boolean>(false)
//    val fav: LiveData<Boolean>
//        get() = _fav
//
//    fun onFav() {
//        if (_fav.value == false) {
//            _fav.postValue(true)
//        } else _fav.postValue(false)
//    }

}