package com.example.adsmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adsmasterclass.adsContainer.InterstitialAd.interstitialAdContainer
import com.example.adsmasterclass.adsContainer.bannerAds.AdMobBanner
import com.example.adsmasterclass.adsContainer.rewardedAd.rewarded
import com.example.adsmasterclass.ui.theme.AdsMasterClassTheme
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobMedium
import com.farimarwat.composenativeadmob.nativead.BannerAdAdmobSmall
import com.farimarwat.composenativeadmob.nativead.rememberNativeAdState
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Thread {

            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? -> }
        }
            .start()
        setContent {
            val adstate = rememberNativeAdState(
                context = this@MainActivity,
                adUnitId = "ca-app-pub-3940256099942544/2247696110"
            )

            AdsMasterClassTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        AdMobBanner(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = { interstitialAdContainer(activity = this@MainActivity) }) {
                            Text(text = "Show interstitialAd Ads")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = { rewarded(activity = this@MainActivity) }) {
                            Text(text = "Show Reward Ads")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        BannerAdAdmobSmall(loadedAd = adstate)
                        Spacer(modifier = Modifier.fillMaxWidth())
                        BannerAdAdmobMedium(loadedAd = adstate)

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdsMasterClassTheme {
        Greeting("Android")
    }

}