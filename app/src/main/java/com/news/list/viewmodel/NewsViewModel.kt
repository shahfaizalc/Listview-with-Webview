package com.news.list.viewmodel

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.list.BR
import com.news.list.injection.Application
import com.news.list.model.ArticlesItem
import com.news.list.utils.DateFormatHandler
import com.news.list.utils.TimeUnits
import com.news.list.utils.ViewModelCallback

/**
 * News View model
 */
class NewsViewModel : ViewModelCallback() {

    private val TAG = "NewsViewModel"

    var articlesList: ObservableArrayList<ArticlesItem>

    /**
     * Specific Blog article link
     */
    private val weblinkBlogArticle = MutableLiveData<String>()

    private var mLastClickTime: Long = 0

    init {
        Application.component.inject(this)
        articlesList = ObservableArrayList()
    }

    @get:Bindable
    var msgView = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msgView)
        }

    @get:Bindable
    var progressBarVisible = View.GONE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    @get:Bindable
    var retry: String? = null
        set(retry) {
            field = retry
            notifyPropertyChanged(BR.retry)
        }

    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    @get:Bindable
    var screenHint = View.VISIBLE
        set(screenHint) {
            field = screenHint
            notifyPropertyChanged(BR.screenHint)
        }

    @get:Bindable
    var retryBtn = View.GONE
        set(retryBtn) {
            field = retryBtn
            notifyPropertyChanged(BR.retryBtn)
        }

    fun doUpdateClickedItem(articlesItem: ArticlesItem) {
        Log.d(TAG, "doUpdateClickedItem: user clicked on " + articlesItem.title);
        if(!handleMultipleClicks()) {
            weblinkBlogArticle.value = articlesItem.url
        }
    }

    /**
     * To get article url
     */
    fun getBlogArticleLink(): LiveData<String> {
        return weblinkBlogArticle
    }

    /**
     * Method to handle multiple click on the item with in short span of time.
     */
    private fun handleMultipleClicks(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    fun timeInUnit(unitDate:String): String {
        return DateFormatHandler().timeIndays(unitDate, TimeUnits.DAYS)
    }
}
