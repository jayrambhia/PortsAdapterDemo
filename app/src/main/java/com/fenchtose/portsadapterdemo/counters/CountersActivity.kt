package com.fenchtose.portsadapterdemo.counters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons_android.utils.visible
import com.fenchtose.portsadapterdemo.driven.counters.CounterDrivenModule
import com.fenchtose.portsadapterdemo.driver.counters.CounterViewModelModule
import com.fenchtose.portsadapterdemo.driver.counters.CountersViewModel
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterHexagonModule

class CountersActivity : AppCompatActivity() {

    companion object {
        fun openCounters(context: Context, counter: CounterListItem) {
            val intent = Intent(context, CountersActivity::class.java).apply {
                putExtra("counter_id", counter.id)
                putExtra("counter_name", counter.name)
            }

            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: CountersViewModel
    private lateinit var counterView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counters)

        title = intent?.getStringExtra("counter_name") ?: "Unknown counter"
        val counterId = intent?.getStringExtra("counter_id") ?: ""

        viewModel = DaggerCounterComponent.builder()
            .driver(CounterViewModelModule(this))
            .hexagon(CounterHexagonModule())
            .driven(CounterDrivenModule(counterId))
            .build()
            .viewModel()

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