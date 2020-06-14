plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

dependencies {
  api(Libs.OkHttp)
  api(Libs.Hyperion.Plugin)

  implementation(Libs.Kotlin.StdLibJdk7)
  implementation(Libs.AndroidX.AppCompat)

  kapt(Libs.AutoService)
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle"))
