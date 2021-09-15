package com.devleejb.ch8_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.KeyEvent
import android.widget.Toast
import com.devleejb.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime // 현재 시간에서 stop할 때 경과된 시간을 뺀다. 이러한 과정을 생략한 경우, 기존의 start 시간으로부터 시간을 측정한다.
            binding.chronometer.start()

            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
            binding.startButton.isEnabled = false
        }

        binding.stopButton.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime() // 경과된 시간 기록을 음수로 기록함.
            binding.chronometer.stop()

            binding.stopButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.resetButton.isEnabled = true
        }

        binding.resetButton.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode === KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "종료하려면 한 번 더 누르세요.", Toast.LENGTH_LONG).show()

                initTime = System.currentTimeMillis()

                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }
}