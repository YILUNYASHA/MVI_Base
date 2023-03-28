package com.example.myapplication.core

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.navigation.DefaultNavigationViewModelDelegateFactory
import com.example.myapplication.network.RemoteService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MvRxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this, viewModelDelegateFactory = DefaultNavigationViewModelDelegateFactory())
        GlobalContext.startKoin {
            androidContext(this@MvRxApplication)
            modules(dadJokeServiceModule)
        }
    }
}
private val dadJokeServiceModule = module {
    factory {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl("https://icanhazdadjoke.com/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(RemoteService::class.java)
    }
}
