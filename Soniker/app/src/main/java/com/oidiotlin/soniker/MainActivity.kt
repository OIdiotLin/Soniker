package com.oidiotlin.soniker

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.oidiotlin.soniker.models.Sonic
import com.oidiotlin.soniker.utils.PlayThread
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var sonic = Sonic(80)
    var mPlayThread: PlayThread? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        freq_textview.text = "80 Hz"
        freq_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                sonic.freq = progress + 80
                freq_textview.text = "${Integer.toString(sonic.freq)} Hz"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        gen_btn.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                freq_seekbar.isEnabled = false
                PlaySound()
            } else {
                freq_seekbar.isEnabled = true
                StopSound()
            }
        })
    }

    fun PlaySound() {
        mPlayThread?.stopPlay()
        mPlayThread = PlayThread(sonic.freq)
        mPlayThread!!.start()
    }

    fun StopSound() {
        mPlayThread?.stopPlay()
    }

}
