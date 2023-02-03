package org.techtown.diffuser.application

import android.app.Application
import org.techtown.diffuser.retrofit.RetrofitClient

class DiffuserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.instance
    }

    companion object {
        const val API_KEY = "44fba6ad8bbac906f3603cd37a51b2b3"
    }
}