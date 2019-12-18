package io.mehow.hyperion.sample

import android.app.Application
import io.mehow.hyperion.Environment
import okhttp3.HttpUrl.Companion.toHttpUrl

class SampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    Environment.init(
        this,
        Environment("GOOGLE", "https://www.google.com".toHttpUrl()),
        Environment("NETFLIX", "https://www.netflix.com".toHttpUrl()),
        Environment("SLACK", "https://www.slack.com".toHttpUrl()),
        Environment("STACKOVERFLOW", "https://www.stackoverflow.com".toHttpUrl())
    )
  }
}
