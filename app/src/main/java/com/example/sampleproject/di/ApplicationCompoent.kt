package com.example.sampleproject.di

import android.content.Context
import com.example.sampleproject.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModules::class, DatabaseModule::class])
interface ApplicationCompoent {


    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationCompoent
    }
}