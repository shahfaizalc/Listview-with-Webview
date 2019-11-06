package com.news.list.communication

import com.news.list.model.NewsApi
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Retrofit service
 */
interface GetServiceNews {

    @GET
    fun retrieveRepositories( @Url url:String): Deferred<NewsApi>

}