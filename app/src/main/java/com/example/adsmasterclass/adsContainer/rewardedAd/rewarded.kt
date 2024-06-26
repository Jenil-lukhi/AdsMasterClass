package com.example.adsmasterclass.adsContainer.rewardedAd

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

fun rewarded(activity: Activity) {

    RewardedAd.load(activity,
        "ca-app-pub-3940256099942544/5224354917",
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                super.onAdLoaded(rewardedAd)
                rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        rewarded(activity = activity)
                        onAdDismissedFullScreenContent()

                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)

                    }
                }
                rewardedAd.show(activity, object : OnUserEarnedRewardListener {
                    override fun onUserEarnedReward(p0: RewardItem) {
                        Toast.makeText(activity, "Add Finished", Toast.LENGTH_SHORT).show()
                    }

                })


            }
        })
}