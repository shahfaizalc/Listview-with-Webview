package com.news.list.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.list.viewmodel.NewsViewModel

/**
 * View model factory
 */
class NewsFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
