package com.news.list.injection

import com.news.list.adapters.NewsRecyclerViewAdapter
import com.news.list.handlers.RecyclerLoadNewsHandler
import com.news.list.injection.module.AndroidModule
import com.news.list.injection.module.ConstantsModule
import com.news.list.injection.module.NetworkStateModule
import com.news.list.injection.module.RestHandlerModule
import com.news.list.utils.DateFormatHandler
import com.news.list.view.NewsFragment
import com.news.list.view.WebViewFragment
import com.news.list.viewmodel.NewsViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class, ConstantsModule::class, NetworkStateModule::class, RestHandlerModule::class))
interface ApplicationComponent {
    fun inject(recyclerLoadNewsHandler: RecyclerLoadNewsHandler)
    fun inject(newsViewModel: NewsViewModel)
    fun inject(newsFragment: NewsFragment)
    fun inject(recyclerViewAdapter: NewsRecyclerViewAdapter)
    fun inject(webViewFragment: WebViewFragment)
    fun inject(dateFormatHandler: DateFormatHandler);

}
