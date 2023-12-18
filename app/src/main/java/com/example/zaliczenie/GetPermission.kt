package com.example.zaliczenie


import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GetPermission : AppCompatActivity() {

    fun CheckPermission(wifiManager: WifiManager){
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted, you can proceed with the action
                    val scanResults = wifiManager.scanResults
                    for (result in scanResults) {
                        //funkcja deprecated
                        val ssid = result.SSID
                        val rssi = result.level
                        val frequency = result.frequency
                        val linkSpeed = result.capabilities

                        // Display or process the information as needed
                        val info = "SSID: $ssid\nRSSI: $rssi\nFrequency: $frequency MHz\nLink Speed: $linkSpeed Mbps"

                        Toast.makeText(applicationContext, info, Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Permission is denied
                    handleDeniedPermission()
                }
            }
    }

    private fun performActionRequiringPermission() {
        // Your code that requires the permission goes here
        // ...
    }

    private fun handleDeniedPermission() {
        // Handle the case where the user denied the permission request
        // You may want to show a message or take other actions
        // ...
    }
}
