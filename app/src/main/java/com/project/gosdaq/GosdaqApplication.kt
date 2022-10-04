package com.project.gosdaq

import android.app.Application
import timber.log.Timber

class GosdaqApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(object: Timber.DebugTree(){
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "GosdaqProject/$tag", message, t)
                }
            })

            Timber.i("BuildConfig: DEBUG")
            Timber.i("Timber Planted!")
        }
    }
}