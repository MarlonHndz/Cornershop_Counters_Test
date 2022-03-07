package com.cornershop.counterstest

import android.app.Application
import com.cornershop.data.dataModule
import com.cornershop.domain.domainModule
import com.cornershop.localdatasource.localDataSourceModule
import com.cornershop.presentation.presentationModule
import com.cornershop.remotedatasource.remoteDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataModule,
                domainModule,
                presentationModule,
                localDataSourceModule,
                remoteDataSourceModule
            )
        }
    }
}
