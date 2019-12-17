package io.mehow.hyperion.sample

import android.app.Application
import io.mehow.hyperion.Environment
import io.mehow.hyperion.HostInterceptor
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor

class SampleApplication : Application() {
  lateinit var interceptor: Interceptor private set

  override fun onCreate() {
    super.onCreate()
    interceptor = HostInterceptor(
        this,
        Environment("GOOGLE", "https://www.google.com".toHttpUrl()),
        Environment("NETFLIX", "https://www.netflix.com".toHttpUrl()),
        Environment("SLACK", "https://www.slack.com".toHttpUrl()),
        Environment("STACKOVERFLOW", "https://www.stackoverflow.com".toHttpUrl())
    )
  }
}
