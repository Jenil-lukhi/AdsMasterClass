package com.example.adsmasterclass

import androidx.multidex.MultiDexApplication
import com.example.adsmasterclass.adsContainer.appOpenAds.AppOpenManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus

class Application : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { initializationStatus: InitializationStatus? -> }
        AppOpenManager(this, "ca-app-pub-3940256099942544/9257395921")
    }
}