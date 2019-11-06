package com.news.list.handlers

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.list.R
import com.news.list.adapters.NewsRecyclerViewAdapter
import com.news.list.communication.RetrofitClientInstance
import com.news.list.injection.Application
import com.news.list.injection.module.ConstantsModule
import com.news.list.model.ArticlesItem
import com.news.list.model.NewsApi
import com.news.list.utils.EndlessRecyclerViewScrollListener
import com.news.list.viewmodel.NewsViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


/**
 *  Class to handle recyclerview scroll listener and to initiate server call
 */
class RecyclerLoadNewsHandler(
    private val newsViewModel: NewsViewModel,
    private val listViewAdapter: NewsRecyclerViewAdapter,
    val view: RecyclerView
) {

    /**
     * Tag
     */
    private val TAG = "NewsHandler"

    @Inject
    lateinit var constantsModule: ConstantsModule

    @Inject
    lateinit var retrofitClientInstance: RetrofitClientInstance

    /**
     * Listener to load more items to the list
     */
    fun scrollListener( linearLayoutManager: LinearLayoutManager) {
        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                initRequest(page)
            }
        }
        view.addOnScrollListener(listener)
    }

    /**
     * function to intialize the request
     * @param page currrent page number
     */
    fun initRequest(page: Int) {
        Log.d(TAG, "initRequest: page "+page)
        Application.component.inject(this)
        newsViewModel.progressBarVisible = View.VISIBLE
        newsViewModel.msgView = View.GONE
        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                newsViewModel.retry = view.context.resources.getString(R.string.news_hint)
                val service = retrofitClientInstance.getServiceNews(
                    constantsModule.baseUrl(), view.context
                )
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveRepositories(constantsModule.subUrl_param() + page)
                        .await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories) }
                newsViewModel.progressBarVisible = View.GONE
            }
        }
    }

    private fun coroutineExceptionHandler() =
        CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "coroutineExceptionHandler:exception $exception")
            newsViewModel.apply {
                progressBarVisible = View.GONE
                if (null != exception.message) {
                    when (articlesList.size < 1) {
                        true -> {
                            retry = view.context.resources.getString(R.string.news_retry)
                            retryBtn = View.VISIBLE
                        }
                        false -> {
                            msgView = View.VISIBLE
                            msg = exception.localizedMessage
                        }
                    }
                }
            }
        }

    private fun coroutineSuccessHandler(response: NewsApi) {
        Log.d(TAG, "coroutineSuccessHandler:success$response")

        newsViewModel.apply {
            screenHint = View.GONE
            retryBtn = View.GONE
            val elements = response.articles as Iterable<ArticlesItem>
            articlesList.addAll(elements)
            view.post { listViewAdapter.notifyDataSetChanged() }
        }
    }
}
