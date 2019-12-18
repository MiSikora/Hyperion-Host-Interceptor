package io.mehow.hyperion.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import io.mehow.hyperion.HostInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val client = OkHttpClient.Builder()
        .addInterceptor(HostInterceptor(this))
        .build()
    findViewById<View>(R.id.button).setOnClickListener {
      thread {
        val request = Request.Builder()
            .url("https://www.squareup.com")
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        runOnUiThread {
          Toast.makeText(this, "${response.request.url}", LENGTH_LONG).show()
        }
      }
    }
  }
}
