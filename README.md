# FM Radio Ultra – Starter v0.2

## Added in v0.2
- Device diagnostic button checks Android FM feature flags, presence of Xiaomi packages `com.miui.fm` and `com.miui.fmservice`, and visible audio output devices.
- The diagnostic is deliberately read-only. It does not claim that a third-party app can control Xiaomi's private FM service.
- Auto Scan remains disabled as a real tuner operation until a compatible backend is identified.

## Important device-specific finding
A publicly indexed Xiaomi FM Radio APK release notes page identifies a build extracted from the Redmi Note 10 Pro on MIUI 14 / Android 13. Xiaomi's FM app/service are OEM components. Their presence is useful evidence, but a normal third-party APK cannot assume access to their private APIs or platform-signature permissions.

A Qualcomm FM project exists for some older Snapdragon devices, but its README states root is required and documents specific Qualcomm V4L2 drivers. It is not confirmed compatible with Redmi Note 10 Pro.

## Build
Open in Android Studio with JDK 17. Run on the Redmi Note 10 Pro and tap CHECK DEVICE. Share the diagnostic text and the phone's Android/MIUI version so the correct backend path can be selected.

## Current honest status
UI + read-only device diagnostics are implemented. Real FM tuning/scanning, audio routing, equalizer, and recording are NOT yet implemented or verified. No APK has been built or tested on the phone.
