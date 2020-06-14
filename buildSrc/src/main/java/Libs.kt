object Libs {
  const val AndroidGradlePlugin = "com.android.tools.build:gradle:4.0.0"

  object Kotlin {
    const val Version = "1.3.61"

    const val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$Version"

    const val StdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$Version"
  }

  object AndroidX {
    const val AppCompat = "androidx.appcompat:appcompat:1.1.0"
  }

  const val OkHttp = "com.squareup.okhttp3:okhttp:4.7.2"

  object Hyperion {
    const val Version = "0.9.27"

    const val Core = "com.willowtreeapps.hyperion:hyperion-core:$Version"

    const val Plugin = "com.willowtreeapps.hyperion:hyperion-plugin:$Version"
  }

  const val AutoService = "com.google.auto.service:auto-service:1.0-rc7"

  const val MavenPublishGradlePlugin = "com.vanniktech:gradle-maven-publish-plugin:0.8.0"

  object Detekt {
    const val Version = "1.3.1"

    const val GradlePluginId = "io.gitlab.arturbosch.detekt"

    const val GradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$Version"

    const val Formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$Version"

    const val Cli = "io.gitlab.arturbosch.detekt:detekt-cli:$Version"
  }

  object GradleVersions {
    const val Version = "0.28.0"

    const val GradlePluginId = "com.github.ben-manes.versions"

    const val GradlePlugin = "com.github.ben-manes:gradle-versions-plugin:$Version"
  }
}
