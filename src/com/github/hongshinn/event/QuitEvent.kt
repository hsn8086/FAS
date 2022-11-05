package com.github.hongshinn.event

import com.github.hongshinn.data.Global
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author hsn
 * @version 1.0
 * @since 2022/5/13 6:20
 */
class QuitEvent : Listener {
    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        try {
            Global.connectCountLock.lock()
            ifTimeOutSetMsgNull(e)
            Global.playerKickCount.remove(e.player.name)
            Global.loginTimeOutCount.remove(e.player.name)
            Global.spamCount.remove(e.player.name)
            Global.canLogin.remove(e.player.name)
            Global.captchaString.remove(e.player.name)
            Global.captcha.remove(e.player.name)
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            Global.connectCountLock.unlock()
        }
    }

    private fun ifTimeOutSetMsgNull(e: PlayerQuitEvent) {
        Global.playerKickCount.putIfAbsent(e.player.name, 0)
        if (Global.playerKickCount[e.player.name] == 1) {
            e.quitMessage = null
        }
    }
}