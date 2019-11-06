package com.news.list.handlers


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.list.adapters.NewsRecyclerViewAdapter
import com.news.list.injection.Application
import com.news.list.injection.ApplicationComponent
import com.news.list.utils.EndlessRecyclerViewScrollListener
import com.news.list.view.NewsFragment
import com.news.list.viewmodel.NewsViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.verify

class RecyclerLoadNewsHandlerTest{


    lateinit var filterViewModel: NewsViewModel

    lateinit var filterFragmentMock: NewsFragment
    lateinit var recyclerLoadHandler: RecyclerLoadNewsHandler
    lateinit var artistRecyclerViewAdapter: NewsRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var applicationComponentMock: ApplicationComponent
    lateinit var linearLayoutManager: LinearLayoutManager


    @Before
    fun setUp() {

        applicationComponentMock = Mockito.mock(ApplicationComponent::class.java)
        filterFragmentMock = Mockito.mock(NewsFragment::class.java)
        Application.component = applicationComponentMock;
        artistRecyclerViewAdapter = Mockito.mock(NewsRecyclerViewAdapter::class.java)
        recyclerView = Mockito.mock(RecyclerView::class.java)


        linearLayoutManager= Mockito.mock(LinearLayoutManager::class.java)

        filterViewModel = NewsViewModel();

        recyclerLoadHandler = RecyclerLoadNewsHandler(
            filterViewModel,
            artistRecyclerViewAdapter,
            recyclerView
        )

    }


    @Test
    fun testinitrequest() {
        recyclerLoadHandler.initRequest(1)

    }
    @Test
    fun testscrolllistener() {
        recyclerLoadHandler.scrollListener( linearLayoutManager)

        verify(recyclerView).addOnScrollListener(ArgumentMatchers.isA(EndlessRecyclerViewScrollListener::class.java))
    }

}