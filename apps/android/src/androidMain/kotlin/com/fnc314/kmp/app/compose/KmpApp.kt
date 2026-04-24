package com.fnc314.kmp.app.compose

import android.app.Application
import com.fnc314.kmp.features.posts.list.postsListModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpApp : Application() {
  override fun onCreate() {
    super.onCreate()
    Napier.base(antilog = DebugAntilog(defaultTag = "com.fnc314.kmp"))
    startKoin {
      androidContext(this@KmpApp)
      analytics {
        setApiKey(BuildKonfig.KOTZILLA_ANALYTICS_KEY)
        setVersion("1.0.0")
      }
      modules(
        postsListModule
      )
    }
  }
}