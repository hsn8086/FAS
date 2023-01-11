package com.github.hsn8086.event

import com.github.hsn8086.data.Global
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @author hsn
 */
class ChatEvent : Listener {
    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent) {
        //����Ƿ���������
        if (Global.chatEnabled) {
            val player = e.player
            //����Ƿ�Ϊ��֤��,���������֤�룬�����Ƿ�Ϊ�ɵ�¼״̬
            Global.canLogin.putIfAbsent(player.name, false)
            if (e.message == Global.captcha[player.name].toString()) {
                Global.canLogin[player.name] = true
                player.sendMessage(Global.verificationSuccessPrompt)
                e.isCancelled = true
                Global.playerKickCount.remove(player.name)
            } else {
                //����ǿɵ�¼״̬�����鷢���Ƿ�Υ��
                if (!Global.canLogin[player.name]!!) {
                    Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                        player.kickPlayer(
                            Global.unauthenticatedActionWarning
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
                        player.sendMessage(Global.talkingTooMuchReminder)
                    }
                    //��鷢���Ƿ����
                    if (Global.spamCount[player.name]!! > 1000) {
                        e.isCancelled = true
                        player.isBanned = true
                        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FAS")) {
                            player.kickPlayer(
                                Global.talkingTooMuchReminder
                            )
                        }
                    }
                    //��鷢���Ƿ����
                    if (e.message.length > Global.maxMessageLength) {
                        e.isCancelled = true
                        e.player.sendMessage(Global.messageTooLongWarning)
                    }
                    //��鷢���Ƿ��зǷ��ַ�
                    for (i in Global.bannedString!!.indices) {
                        if (e.message.contains(Global.bannedString!![i])) {
                            e.isCancelled = true
                            e.player.sendMessage(Global.bannedStringWarning)
                        }
                    }
                }
            }
        } else {
            e.isCancelled = true
            e.player.sendMessage(Global.chatDisabledWarning)
        }
    }
}