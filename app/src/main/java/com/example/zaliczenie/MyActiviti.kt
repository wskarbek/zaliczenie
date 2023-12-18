package com.example.zaliczenie

import android.app.Activity
import android.os.Bundle

class MyActivity : Activity(), WifiScanService {
    private lateinit var wifiScanService: WifiScanService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        wifiScanService = WifiScanService();

        wifiScanService.onCreate()
    }
}