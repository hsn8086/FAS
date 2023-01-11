package com.github.hsn8086.event

import com.github.hsn8086.data.Global
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.awt.image.BufferedImage

/**
 * @author hsn
 * @version 1.0
 * @since 2022/05/14 4:52
 */
class PingEvent : Listener {
    @EventHandler
    fun onPing(e: ServerListPingEvent) {
        val ipAddress =
            e.address.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].replace("/", "")
        Global.pingCount.merge(ipAddress, 1) { a: Int?, b: Int? ->
            Integer.sum(
                a!!, b!!
            )
        }
        if (Global.pingCount[ipAddress]!! > Global.maximumPingsPerSecond) {
            e.motd = ""
            try {
                e.setServerIcon(Bukkit.loadServerIcon(BufferedImage(64, 64, 1)))
            } catch (exception: Exception) {
                Bukkit.getLogger().warning(exception.toString())
            }
        }
    }
}