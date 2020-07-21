package com.rohitTheBest.owlbotdictionaryretrofit.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Dictionary
import com.rohitTheBest.owlbotdictionaryretrofit.data.repository.MainRepository
import com.rohitTheBest.owlbotdictionaryretrofit.util.NetworkHelper
import com.rohitTheBest.owlbotdictionaryretrofit.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class DictionaryViewModel @ViewModelInject constructor(
    val repository: MainRepository,
    val networkHelper: NetworkHelper
) : ViewModel() {

    private val _meaning = MutableLiveData<Resources<Dictionary>>()

    val meaning: LiveData<Resources<Dictionary>>
        get() = _meaning


    fun fetchMeaning(word: String) {

        viewModelScope.launch {

            _meaning.postValue(Resources.Loading())
            if (networkHelper.isNetworkConnected()) {

                repository.getMeaning(word).let {

                    if (it.isSuccessful) {

                        _meaning.postValue(handleResponse(it))
                    } else {
                        _meaning.postValue(Resources.Error(it.errorBody().toString()))
                    }
                }
            } else {
                _meaning.postValue(Resources.Error("No Internet Available!!!", null))
            }
        }
    }

    private fun handleResponse(response: Response<Dictionary>): Resources<Dictionary>? {

        if (response.isSuccessful) {

            response.body()?.let {

                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }
}
