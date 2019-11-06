package com.news.list.view

import android.content.Context
import android.content.res.Resources
import com.news.list.databinding.ActivityNewsBinding
import com.news.list.injection.Application
import com.news.list.injection.ApplicationComponent
import com.news.list.utils.NetworkStateHandler
import com.news.list.viewmodel.NewsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class NewsFragmentTest {

    lateinit var networkStateHandlerMock: NetworkStateHandler

    lateinit var activityNewsBinding : ActivityNewsBinding

    lateinit var applicationComponentMock: ApplicationComponent

    lateinit var newsViewModel: NewsViewModel;

    lateinit var newsFragment: NewsFragment

    @Mock
    lateinit var contextMock: Context

    lateinit var resourseMock: Resources


    @Before
    fun setUp() {
        val application = Application()
        application.onCreate()
        applicationComponentMock = Mockito.mock(ApplicationComponent::class.java)
        networkStateHandlerMock = Mockito.mock(NetworkStateHandler::class.java)
        contextMock = Mockito.mock(Context::class.java)
        resourseMock = Mockito.mock(Resources::class.java)
        activityNewsBinding = Mockito.mock(ActivityNewsBinding::class.java)
        Application.component = applicationComponentMock;
        newsFragment = NewsFragment()

        newsViewModel = NewsViewModel()
        newsFragment.networkStateHandler = networkStateHandlerMock
        newsFragment.binding =  activityNewsBinding

        Mockito.`when`(newsFragment.binding!!.mainData).thenReturn(newsViewModel)
    }

    @Test
    fun testonNetworkStateReceivedOn() {
        newsFragment.onNetworkStateReceived(true)
        Assert.assertEquals(newsViewModel.msgView, 0x00000008)
    }

}