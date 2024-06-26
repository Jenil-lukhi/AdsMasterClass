package com.example.adsmasterclass.adsContainer.appOpenAds

import android.app.Activity
import android.content.Context
import android.media.tv.AdRequest
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.adsmasterclass.Application
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.Date

interface OnShowAdCompleteListener {
    fun onShowAdComplete()
}



class AppOpenManager: android.app.Application.ActivityLifecycleCallbacks {
    private var currentActivity: Activity? = null
    private var adUnit: String

    private var lifecycleEventObserver = LifecycleEventObserver { source, event ->

        if (event == Lifecycle.Event.ON_RESUME) {
            currentActivity?.let {
                showAdIfAvailable(it)
            }
        } else if (event == Lifecycle.Event.ON_PAUSE){

        }
    }



    constructor(application: Application, adUnitId: String) {
        this.adUnit=adUnitId
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }


    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    private var isShowingAd = false
    private var loadTime: Long = 0

    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }
        isLoadingAd = true
        var request = com.google.android.gms.ads.AdRequest.Builder().build()

        AppOpenAd.load(
            context,
            adUnit,
            request,
            object : AppOpenAdLoadCallback(){

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    isLoadingAd=false
                }

                override fun onAdLoaded(p0: AppOpenAd) {
                    super.onAdLoaded(p0)
                    appOpenAd = p0
                    isLoadingAd = false
                    loadTime = Date().time
                }
            }
        )

    }

    private fun isAdAvailable(): Boolean {

        return appOpenAd!=null

    }

    private fun showAdIfAvailable(it: Activity) {

        showAdIfAvailable(
            it,
            object : OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    // Empty.
                }
            }
        )

    }

    private fun showAdIfAvailable(it: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {

        if (isShowingAd) {
            return
        }
        if (!isAdAvailable()){
            onShowAdCompleteListener.onShowAdComplete()
            loadAd(it)
            return
        }
        appOpenAd!!.fullScreenContentCallback
        object : FullScreenContentCallback(){
            override fun onAdShowedFullScreenContent() {



            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                appOpenAd=null
                isShowingAd=false
                onShowAdCompleteListener.onShowAdComplete()
                loadAd(it)

            }

            override fun onAdDismissedFullScreenContent() {
                appOpenAd=null
                isShowingAd=false
                onShowAdCompleteListener.onShowAdComplete()
                loadAd(it)
            }
        }
        isShowingAd=true
        appOpenAd!!.show(it)
    }
}