package io.mehow.hyperion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.willowtreeapps.hyperion.plugin.v1.PluginModule

internal class HostInterceptorPluginModule : PluginModule() {
  override fun createPluginView(layoutInflater: LayoutInflater, parent: ViewGroup): View {
    val view = layoutInflater.inflate(R.layout.io_mehow_hhi_plugin_item, parent, false)
    val spinner = view.findViewById<AppCompatSpinner>(R.id.io_mehow_hhi_hosts)

    val preferences = Environment.preferences(parent.context)
    val adapter = EnvironmentAdapter(preferences.getAllEnvironments(), layoutInflater)
    val selectedItemPosition = adapter.positionOf(preferences.getSelectedEnvironment())

    spinner.adapter = adapter
    spinner.setSelection(selectedItemPosition)

    var ignoreItem = true
    spinner.onItemSelectedListener = object : OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (ignoreItem) {
          ignoreItem = false
          return
        }
        val item = adapter.getItem(position)
        preferences.saveSelectedEnvironment(item)
      }

      override fun onNothingSelected(parent: AdapterView<*>?) = Unit
    }
    return view
  }

  private class EnvironmentAdapter(
    private val environments: List<Environment>,
    private val layoutInflater: LayoutInflater
  ) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      val viewHolder = if (convertView == null) {
        val view = layoutInflater.inflate(R.layout.io_mehow_hhi_spinner_item, parent, false)
        ViewHolder(view as AppCompatTextView).apply { view.tag = this }
      } else convertView.tag as ViewHolder
      viewHolder.bindTo(getItem(position))
      return viewHolder.item
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
      val viewHolder = if (convertView == null) {
        val view = layoutInflater.inflate(R.layout.io_mehow_hhi_drop_down_item, parent, false)
        ViewHolder(view as AppCompatTextView).apply { view.tag = this }
      } else convertView.tag as ViewHolder
      viewHolder.bindTo(getItem(position))
      return viewHolder.item
    }

    override fun getItem(position: Int) = environments[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = environments.size

    fun positionOf(environment: Environment): Int {
      return environments.indexOf(environment)
    }
  }

  private class ViewHolder(val item: AppCompatTextView) {
    fun bindTo(environment: Environment) {
      this.item.text = environment.name
    }
  }
}
