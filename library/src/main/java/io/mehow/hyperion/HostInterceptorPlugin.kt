package io.mehow.hyperion

import com.google.auto.service.AutoService
import com.willowtreeapps.hyperion.plugin.v1.Plugin
import com.willowtreeapps.hyperion.plugin.v1.PluginModule

@AutoService(Plugin::class)
internal class HostInterceptorPlugin : Plugin() {
  override fun minimumRequiredApi() = 21
  override fun createPluginModule(): PluginModule {
    return HostInterceptorPluginModule()
  }
}
