package org.techtown.diffuser.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp  // 이 클래스에 반드시 annotate 해야하는 Kick off code.
class DiffuserApp : Application()