package io.mehow.hyperion

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

data class Environment(val name: String, val hostUrl: HttpUrl) {
  internal fun encode(): String {
    return "$name|$hostUrl"
  }

  companion object {
    internal val None = Environment("DEFAULT", "https://www.example.com".toHttpUrl())

    internal fun decode(value: String): Environment? {
      val name = value.substringBeforeLast('|')
      val hostUrl = value.substringAfterLast('|').toHttpUrlOrNull()
      return if (name == value || hostUrl == null) null
      else Environment(name, hostUrl)
    }

    internal fun preferences(context: Context): SharedPreferences {
      return context.getSharedPreferences("hyperion-host-interceptor", MODE_PRIVATE)
    }

    @JvmStatic fun init(context: Context, vararg environments: Environment) {
      val preferences = preferences(context)
      val filteredEnvironments = environments
          .distinctBy { it.name }
          .filter { it.name != None.name }
          .sortedBy { it.name } + None

      val selectedEnvironment = preferences.getSelectedEnvironment()
      preferences.edit().clear().apply()
      if (selectedEnvironment in filteredEnvironments) {
        preferences.saveSelectedEnvironment(selectedEnvironment)
      }
      for (environment in filteredEnvironments) {
        preferences.saveEnvironment(environment)
      }
    }
  }
}

internal fun SharedPreferences.getAllEnvironments(): List<Environment> {
  return listOf(Environment.None) + all.entries
      .asSequence()
      .map { (_, value) -> value }
      .filterIsInstance<String>()
      .mapNotNull(Environment::decode)
      .filter { it != Environment.None }
      .distinct()
      .sortedBy { it.name }
      .toList()
}

internal fun SharedPreferences.saveEnvironment(environment: Environment) {
  val encoded = environment.encode()
  edit().putString(environment.name, encoded).apply()
}

private const val SelectedEnvironment = "SelectedEnvironment"

internal fun SharedPreferences.saveSelectedEnvironment(environment: Environment) {
  edit().putString(SelectedEnvironment, environment.encode()).apply()
}

internal fun SharedPreferences.getSelectedEnvironment(): Environment {
  return getString(SelectedEnvironment, null)?.run(Environment::decode) ?: Environment.None
}
