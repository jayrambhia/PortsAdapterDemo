package com.fenchtose.portsadapterdemo

import android.app.Application
import com.fenchtose.portsadapterdemo.base.ApplicationComponent
import com.fenchtose.portsadapterdemo.base.DaggerApplicationComponent
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule
import javax.inject.Inject

class PortsAdaptersApp : Application() {

    @Inject lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .coroutines(AppCoroutinesContextProviderModule())
            .build()
            .inject(this)
    }
}