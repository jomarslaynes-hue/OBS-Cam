package com.obscam.app

import android.content.Context
import android.net.wifi.WifiManager
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

object NetworkInfo {
    fun getLocalIpAddress(context: Context): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
        val ip = wifiManager?.connectionInfo?.ipAddress ?: return "192.168.1.10"
        val address = InetAddress.getByAddress(
            byteArrayOf(
                (ip and 0xFF).toByte(),
                (ip shr 8 and 0xFF).toByte(),
                (ip shr 16 and 0xFF).toByte(),
                (ip shr 24 and 0xFF).toByte()
            )
        )
        return address.hostAddress ?: "192.168.1.10"
    }

    fun getLocalIpAddressFallback(): String {
        return try {
            NetworkInterface.getNetworkInterfaces()?.toList()
                ?.flatMap { it.inetAddresses?.toList() ?: emptyList() }
                ?.firstOrNull { !it.isLoopbackAddress && it.hostAddress?.contains(":") == false }
                ?.hostAddress
                ?: "192.168.1.10"
        } catch (_: SocketException) {
            "192.168.1.10"
        }
    }
}
