package com.fenchtose.portsadapterdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fenchtose.portsadapterdemo.commons_android.utils.visible
import com.fenchtose.portsadapterdemo.driven.counters.CountersMap
import com.fenchtose.portsadapterdemo.driven.counters.NoOpCounter
import com.fenchtose.portsadapterdemo.driver.counters.CountersViewModel
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterPort
import com.fenchtose.portsadapterdemo.hexagon.counters.CountersModule
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDriverPort

class CountersActivity : AppCompatActivity() {

    companion object {
        fun openCounters(context: Context, counter: CounterListItem) {
            val intent = Intent(context, CountersActivity::class.java).apply {
                putExtra("counter_id", counter.id)
            }

            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: CountersViewModel
    private lateinit var counterView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counters)

        val counter: CounterPort =
            CountersMap.map[intent?.getStringExtra("counter_id")]?.let { it() } ?: NoOpCounter()

        viewModel = ViewModelProviders.of(this, CountersViewModelFactory(CountersModule(counter)))
            .get(CountersViewModel::class.java)

        counterView = findViewById(R.id.count)
        findViewById<View>(R.id.increment).setOnClickListener { viewModel.increment() }
        findViewById<View>(R.id.reduction).setOnClickListener { viewModel.reduction() }

        viewModel.state().observe(this, Observer { state ->
            counterView.text = "${state.number}"
            findViewById<View>(R.id.increment).visible(state.canIncrease)
            findViewById<View>(R.id.reduction).visible(state.canReduce)
        })
    }
}

@Suppress("UNCHECKED_CAST")
class CountersViewModelFactory(private val port: CounterDriverPort) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountersViewModel(port) as T
    }
}