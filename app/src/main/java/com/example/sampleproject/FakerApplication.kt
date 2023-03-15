package com.example.sampleproject

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.sampleproject.di.ApplicationCompoent
import com.example.sampleproject.di.DaggerApplicationCompoent
import dagger.Component

class FakerApplication : MultiDexApplication() {

    lateinit var applicationCompoent: ApplicationCompoent
    override fun onCreate() {
        super.onCreate()

        applicationCompoent = DaggerApplicationCompoent.factory().create(this)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}