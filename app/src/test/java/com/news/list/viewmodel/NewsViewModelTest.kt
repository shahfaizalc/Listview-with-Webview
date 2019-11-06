package com.news.list.viewmodel

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.news.list.injection.Application
import com.news.list.injection.ApplicationComponent
import com.news.list.utils.NetworkStateHandler
import com.news.list.view.NewsFragment
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class NewsViewModelTest {

    lateinit var networkStateHandlerMock: NetworkStateHandler

    lateinit var applicationComponentMock: ApplicationComponent

    lateinit var newsViewModel: NewsViewModel;

    lateinit var newsFragment: NewsFragment

    @Mock
    lateinit var contextMock:Context

    lateinit var resourseMock:Resources

    @Before
    fun setUp() {
        val application= Application()
        application.onCreate()
        applicationComponentMock = mock(ApplicationComponent::class.java)
        networkStateHandlerMock = mock(NetworkStateHandler::class.java)
        contextMock = mock(Context::class.java)
        resourseMock = mock(Resources::class.java)
        Application.component = applicationComponentMock;
        newsFragment = mock(NewsFragment::class.java)
        newsViewModel = NewsViewModel()
    }


    @Test
    fun getMsgView() {
        newsViewModel.msgView = View.GONE
        assert(newsViewModel.msgView == View.GONE)

    }

    @Test
    fun getProgressBarVisible() {
        newsViewModel.progressBarVisible = View.GONE
        assert(newsViewModel.progressBarVisible == View.GONE)
    }


    @Test
    fun getRetry() {
        newsViewModel.retry = "sampleText"
        assert(newsViewModel.retry.equals( "sampleText"))
    }

    @Test
    fun getMsg() {
        newsViewModel.msg = "sampleText"
        assert(newsViewModel.msg.equals( "sampleText"))
    }

    @Test
    fun getScreenHint() {
        newsViewModel.screenHint = View.GONE
        assert(newsViewModel.screenHint == View.GONE)
    }

    @Test
    fun getRetryBtn() {
        newsViewModel.retryBtn = View.GONE
        assert(newsViewModel.retryBtn == View.GONE)
    }

}