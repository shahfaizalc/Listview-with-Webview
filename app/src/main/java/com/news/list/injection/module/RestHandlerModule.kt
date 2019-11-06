package com.news.list.injection.module

import com.news.list.communication.RetrofitClientInstance
import dagger.Module
import dagger.Provides


@Module
class RestHandlerModule() {

    @Provides
    fun provideRestHandler(): RetrofitClientInstance = RetrofitClientInstance()

}