package com.fmradio.ultra

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build

/**
 * Safe capability probe only. It does NOT access or control the FM tuner.
 * Xiaomi's FM app/service are OEM components and their presence does not
 * grant a third-party app permission to call their private interfaces.
 */
object DeviceProbe {
    fun run(context: Context): String {
        val pm = context.packageManager
        val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val fmFeature = pm.hasSystemFeature("android.hardware.radio.fm") ||
            pm.hasSystemFeature(PackageManager.FEATURE_BROADCAST_RADIO)
        val miuiFm = isInstalled(pm, "com.miui.fm")
        val miuiService = isInstalled(pm, "com.miui.fmservice")
        val outputs = if (Build.VERSION.SDK_INT >= 23) {
            audio.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
                .map { describe(it) }
                .distinct()
                .joinToString()
                .ifBlank { "No output device reported" }
        } else {
            "Audio device details require Android 6.0+"
        }

        return buildString {
            appendLine("FM feature flag: ${if (fmFeature) "reported" else "not reported"}")
            appendLine("Xiaomi FM app: ${if (miuiFm) "found" else "not found"}")
            appendLine("Xiaomi FM service: ${if (miuiService) "found" else "not found"}")
            appendLine("Audio outputs: $outputs")
            append("Result: diagnostic only; FM tuner access is not yet connected.")
        }
    }

    private fun isInstalled(pm: PackageManager, packageName: String): Boolean =
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                pm.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(packageName, 0)
            }
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }

    private fun describe(device: AudioDeviceInfo): String = when (device.type) {
        AudioDeviceInfo.TYPE_BUILTIN_SPEAKER -> "Speaker"
        AudioDeviceInfo.TYPE_WIRED_HEADPHONES -> "Wired headphones"
        AudioDeviceInfo.TYPE_WIRED_HEADSET -> "Wired headset"
        AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
        AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> "Bluetooth"
        else -> "Audio device type ${device.type}"
    }
}
