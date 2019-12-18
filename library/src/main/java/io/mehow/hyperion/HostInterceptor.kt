package io.mehow.hyperion

import android.content.Context
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class HostInterceptor(
  context: Context
) : Interceptor {
  private val preferences = Environment.preferences(context)

  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    val environment = preferences.getSelectedEnvironment()

    val newRequest = if (environment == Environment.None) request
    else request.changeHost(environment.hostUrl)

    return chain.proceed(newRequest)
  }

  private fun Request.changeHost(host: HttpUrl): Request {
    val newUrl = url.newBuilder()
        .scheme(host.scheme)
        .host(host.toUrl().host)
        .port(host.port)
        .build()
    return newBuilder().url(newUrl).build()
  }
}
