package com.news.list.injection.module

import com.news.list.utils.NetworkStateHandler
import dagger.Module
import dagger.Provides


@Module
open class NetworkStateModule() {

    @Provides
    fun provideApplicationContext(): NetworkStateHandler = NetworkStateHandler()

}