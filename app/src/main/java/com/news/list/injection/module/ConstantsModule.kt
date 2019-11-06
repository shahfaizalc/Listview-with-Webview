package com.news.list.injection.module

import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ConstantsModule @Inject constructor() {

    @Provides
    fun baseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    fun subUrl_param(): String = "everything?q=sports&apiKey="+getApiKey()+"&page="

    @Provides
    fun getApiKey(): String = "7019973f03494525b62199f2e92fe71f";

    @Provides
    fun BUNDLE_KEY_URL() : String = "blog_url";


}

