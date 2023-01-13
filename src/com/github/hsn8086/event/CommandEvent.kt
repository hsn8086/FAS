package com.github.hsn8086.event

import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

/**
 * @author hsn
 * @version 4.1
 */
class CommandEvent : Listener {
    @EventHandler
    fun onPlayerChat(e: PlayerCommandPreprocessEvent) {

        //��������Ƿ��ڻ����嵥��

        if (Config.antiCommand) {
            var inWhiteList = false

            for (i in Config.commandWhiteList!!) {
                if (e.message.startsWith(i)) {
                    inWhiteList = true
                    break
                }
            }
            if (!inWhiteList) {
                val player = e.player
                //����ǿɵ�¼״̬�����鷢���Ƿ�Υ��
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
                    //��鷢��Ƶ��
                    if (Global.spamCount[player.name]!! > -100) {
                        e.isCancelled = true
                        player.sendMessage(Config.talkingTooMuchReminder)
                    }
                    //��鷢���Ƿ����
                    if (Global.spamCount[player.name]!! > 1000) {
                        e.isCancelled = true
                        if (Config.mode == "ban") {
                            player.isBanned = true
                        }
                        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                            player.kickPlayer(
                                Config.talkingTooMuchReminder
                            )
                        }
                    }
                }
            }
        }
    }
}