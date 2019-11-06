package com.news.list.injection

import android.app.Application
import com.news.list.injection.module.AndroidModule
import com.news.list.injection.module.ConstantsModule
import com.news.list.injection.module.NetworkStateModule
import com.news.list.injection.module.RestHandlerModule

class Application : Application() {


    companion object {
        @JvmStatic
        lateinit var component: ApplicationComponent
    }


    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .networkStateModule(NetworkStateModule())
                .restHandlerModule(RestHandlerModule())
                .constantsModule(ConstantsModule())
                .build()

    }


}