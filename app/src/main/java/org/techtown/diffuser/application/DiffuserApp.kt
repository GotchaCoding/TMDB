package org.techtown.diffuser.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiffuserApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        RetrofitClient.instance
    }

    companion object {
        const val API_KEY = "44fba6ad8bbac906f3603cd37a51b2b3"
    }
}