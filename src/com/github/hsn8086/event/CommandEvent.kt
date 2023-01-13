package com.github.hsn8086.event

import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

/**
 * @author hsn
 * @version 4.0
 */
class CommandEvent : Listener {
    @EventHandler
    fun onPlayerChat(e: PlayerCommandPreprocessEvent) {

        //检查命令是否在豁免清单内
        var inWhiteList: Boolean = false
        for (i in Config.commandWhiteList!!) {
            if (e.message.startsWith(i)) {
                inWhiteList = true
                break
            }
        }
        if (!inWhiteList) {
            val player = e.player
            //如果是可登录状态，则检查发言是否违规
            if (!Global.canLogin[player.name]!!) {


                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                    player.kickPlayer(
                        Config.unauthenticatedActionWarning
                    )
                }
                e.isCancelled = true

            } else {
                Global.spamCount.merge(player.name, 100) { a: Int, b: Int ->
                    Integer.sum(
                        a, b
                    )
                }
                //检查发言频率
                if (Global.spamCount[player.name]!! > -100) {
                    e.isCancelled = true
                    player.sendMessage(Config.talkingTooMuchReminder)
                }
                //检查发言是否过多
                if (Global.spamCount[player.name]!! > 1000) {
                    e.isCancelled = true
                    player.isBanned = true
                    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                        player.kickPlayer(
                            Config.talkingTooMuchReminder
                        )
                    }
                }
                //检查发言是否过长
                if (e.message.length > Config.maxMessageLength) {
                    e.isCancelled = true
                    e.player.sendMessage(Config.messageTooLongWarning)
                }
                //检查发言是否含有非法字符
                for (i in Config.bannedString!!.indices) {
                    if (e.message.contains(Config.bannedString!![i])) {
                        e.isCancelled = true
                        e.player.sendMessage(Config.bannedStringWarning)
                    }
                }

            }

        }
    }
}