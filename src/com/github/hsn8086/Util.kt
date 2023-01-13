package com.github.hsn8086

import org.bukkit.Bukkit




/**
 * @author hsn
 * @since 1/13/2023 8:03 下午
 * @version 1.0
 */
object Util {
    fun isMC5(): Boolean {
        return Bukkit.getBukkitVersion().contains("1.5")
    }
    fun isMC6(): Boolean {
        return Bukkit.getBukkitVersion().contains("1.6")
    }
    fun isMC7(): Boolean {
        return Bukkit.getBukkitVersion().contains("1.7")
    }

}