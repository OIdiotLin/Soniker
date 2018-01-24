package com.oidiotlin.soniker.utils

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack


/**
 * Package: com.oidiotlin.soniker.utils
 * Created by oidiotlin on 18-1-24.
 */


class PlayThread(rate: Int) : Thread() {

    internal var mAudioTrack: AudioTrack? = null

    internal var length: Int = 0
    internal var waveLen: Int = 0
    internal var freq: Int = 0

    internal lateinit var wave: ByteArray

    init {
        freq = rate
        waveLen = RATE / freq
        length = waveLen * freq
        wave = ByteArray(RATE)
        mAudioTrack = AudioTrack(AudioManager.STREAM_MUSIC, RATE,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                AudioFormat.ENCODING_PCM_8BIT, length, AudioTrack.MODE_STREAM)
        mAudioTrack!!.setStereoVolume(1f,1f)
        ISPLAYSOUND = true
        makePureTone()

    }

    private fun makePureTone() {
        for (i in wave.indices) {
            val x = 1 - Math.sin(2 * Math.PI * ((i % waveLen) / waveLen.toFloat()))
            wave[i] = ((Byte.MAX_VALUE * x).toByte())
        }
    }

    override fun run() {
        super.run()
        if (null != mAudioTrack)
            mAudioTrack!!.play()
        while (ISPLAYSOUND) {
            mAudioTrack!!.write(wave, 0, length)
        }

    }

    fun stopPlay() {
        ISPLAYSOUND = false
        releaseAudioTrack()
    }

    private fun releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack!!.stop()
            mAudioTrack!!.release()
            mAudioTrack = null
        }
    }

    companion object {
        val RATE = 44100
        var ISPLAYSOUND: Boolean = false
    }
}