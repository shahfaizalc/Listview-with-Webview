package com.news.list.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.list.viewmodel.WebViewModel

class WebViewFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebViewModel::class.java)) {
            return WebViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
