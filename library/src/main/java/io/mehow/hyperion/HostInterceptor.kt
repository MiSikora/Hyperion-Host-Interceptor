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

    val newRequest = when {
      environment == Environment.None -> request
      request.headers[SkipHeaderName]?.toBoolean() == true -> request
      else -> request.changeHost(environment.hostUrl)
    }.removeSkipHeader()

    return chain.proceed(newRequest)
  }

  private fun Request.removeSkipHeader(): Request {
    return newBuilder().removeHeader(SkipHeaderName).build()
  }

  private fun Request.changeHost(host: HttpUrl): Request {
    val newUrl = url.newBuilder()
        .scheme(host.scheme)
        .host(host.toUrl().host)
        .port(host.port)
        .build()
    return newBuilder().url(newUrl).build()
  }

  companion object {
    const val SkipHeaderName = "Skip-Host-Interception"
    const val SkipHeader = "$SkipHeaderName: true"
  }
}
