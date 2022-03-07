package com.cornershop.counterstest

import android.app.Application
import com.cornershop.data.di.dataModule
import com.cornershop.domain.di.domainModule
import com.cornershop.localdatasource.di.localDataSourceModule
import com.cornershop.presentation.di.presentationModule
import com.cornershop.remotedatasource.di.remoteDataSourceModule
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
