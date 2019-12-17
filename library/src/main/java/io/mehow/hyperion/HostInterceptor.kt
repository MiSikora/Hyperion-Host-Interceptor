package io.mehow.hyperion

import android.content.Context
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class HostInterceptor(
  context: Context,
  vararg environments: Environment
) : Interceptor {
  private val preferences = Environment.preferences(context)

  init {
    val filteredEnvironments = environments
        .distinctBy { it.name }
        .filter { it.name != Environment.None.name }
        .sortedBy { it.name } + Environment.None

    val selectedEnvironment = preferences.getSelectedEnvironment()
    preferences.edit().clear().apply()
    if (selectedEnvironment in filteredEnvironments) {
      preferences.saveSelectedEnvironment(selectedEnvironment)
    }
    for (environment in filteredEnvironments) {
      preferences.saveEnvironment(environment)
    }
  }

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
