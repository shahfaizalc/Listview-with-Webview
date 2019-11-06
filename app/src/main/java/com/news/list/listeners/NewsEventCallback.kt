package com.news.list.listeners

import com.news.list.viewmodel.NewsViewModel

/**
 * Interface to notify news list item click events
 */
interface NewsEventCallback {

    /**
     * on list item click
     */
    fun onClickNewsListItem(newsViewModel : NewsViewModel, position: Int)

}