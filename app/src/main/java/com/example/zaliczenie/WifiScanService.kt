package com.example.zaliczenie

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.IBinder
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest;
import android.app.Activity;
import com.example.zaliczenie.GetPermission


class WifiScanService : Service() {

    private lateinit var wifiManager: WifiManager
    private lateinit var activity: Activity;

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate(activity: Activity) {
        super.onCreate()
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        this.activity = activity
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startWifiScan(this.activity)
        return START_STICKY
    }

    private fun startWifiScan(activity: Activity) {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            scanWifiNetworks()
        }
    }

    private fun scanWifiNetworks() {
        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val success = intent?.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success == true) {
                    val scanResults = wifiManager.scanResults
                    for (result in scanResults) {
                        val ssid = result.SSID
                        val rssi = result.level
                        val frequency = result.frequency
                        val linkSpeed = result.capabilities

                        val info = "SSID: $ssid\nRSSI: $rssi\nFrequency: $frequency MHz\nLink Speed: $linkSpeed Mbps"

                        Toast.makeText(applicationContext, info, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        val success = wifiManager.startScan()
        if (!success) {
            //cośtam
        }
    }
}
