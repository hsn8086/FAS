package com.github.hsn8086.event

import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @author hsn
 * @version 4.0
 */
class ChatEvent : Listener {
    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent) {
        //����Ƿ���������
        if (Config.chatEnabled) {
            val player = e.player
            //����Ƿ�Ϊ��֤��,���������֤�룬�����Ƿ�Ϊ�ɵ�¼״̬
            Global.canLogin.putIfAbsent(player.name, false)
            if (e.message == Global.captcha[player.name].toString()) {
                Global.canLogin[player.name] = true
                player.sendMessage(Config.verificationSuccessPrompt)
                e.isCancelled = true
                Global.playerKickCount.remove(player.name)
            } else {
                //����ǿɵ�¼״̬�����鷢���Ƿ�Υ��
                if (!Global.canLogin[player.name]!!) {
                    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                        player.kickPlayer(
                            Config.unauthenticatedActionWarning
                        )
                    }
                    e.isCancelled = true
                } else {
                    Global.spamCount.merge(player.name, 200) { a: Int?, b: Int? ->
                        Integer.sum(
                            a!!, b!!
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
                        player.isBanned = true
                        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                            player.kickPlayer(
                                Config.talkingTooMuchReminder
                            )
                        }
                    }
                    //��鷢���Ƿ����
                    if (e.message.length > Config.maxMessageLength) {
                        e.isCancelled = true
                        e.player.sendMessage(Config.messageTooLongWarning)
                    }
                    //��鷢���Ƿ��зǷ��ַ�
                    for (i in Config.bannedString!!.indices) {
                        if (e.message.contains(Config.bannedString!![i])) {
                            e.isCancelled = true
                            e.player.sendMessage(Config.bannedStringWarning)
                        }
                    }
                }
            }
        } else {
            e.isCancelled = true
            e.player.sendMessage(Config.chatDisabledWarning)
        }
    }
}