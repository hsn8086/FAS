package com.github.hongshinn.event

import com.github.hongshinn.data.Global
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

/**
 * @author hsn
 * @version 1.2
 * @since 2022-5-13
 */
class LoginEvent : Listener {
    @EventHandler
    fun onPlayerLogin(e: AsyncPlayerPreLoginEvent) {
        //锁
        Global.activityLock.lock()
        //初始化玩家
        Global.whitelist.putIfAbsent(e.name, 0)
        //检测玩家是否在白名单内
        if (Global.whitelist[e.name]!! >= 1 && Global.whileListEnabled) {
            //减少一次玩家的豁免权
            Global.whitelist.merge(e.name, -1) { a: Int?, b: Int? ->
                Integer.sum(
                    a!!, b!!
                )
            }
            Global.activityLock.unlock()
        } else {
            Global.activityLock.unlock()
            try {
                Global.connectCountLock.lock()
                //清空列表
                if (Global.canLoginPlayer.size > 1141) {
                    Global.canLoginPlayer.clear()
                }
                //初始化玩家
                Global.canLoginPlayer.putIfAbsent(e.address.toString(), false)

                //增加连接计次
                if (Global.connectCount < 50) {
                    Global.connectCount++
                }

                //当连接计次到达一定水平后踢出
                if (Global.connectCount > 0) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                        Global.kickedForUnderAttack
                    )
                    return
                }

                //当玩家未通过重进验证是踢出
                if (!Global.canLoginPlayer[e.address.toString()]!!) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                        Global.kickedForCheck
                    )
                    Global.canLoginPlayer[e.address.toString()] = true
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                Global.connectCountLock.unlock()
            }
        }
    }
}