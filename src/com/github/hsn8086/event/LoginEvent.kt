package com.github.hsn8086.event

import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.concurrent.ConcurrentHashMap

/**
 * @author hsn
 * @version 4.0
 * @since 2022-5-13
 */
class LoginEvent : Listener {
    private var canLoginPlayer = ConcurrentHashMap<String, Boolean>()

    @EventHandler
    fun onPlayerLogin(e: AsyncPlayerPreLoginEvent) {
        //初始化玩家
        Global.whitelist.putIfAbsent(e.name, 0)

        if (Config.banMCStormFreePlanBot && e.name.startsWith("MCSTORM")) {
            e.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Config.kickedForIllegalName
            )
        }

        //检测玩家是否在白名单内
        if (Global.whitelist[e.name]!! >= 1 && Config.whileListEnabled) {
            //减少一次玩家的豁免权
            Global.whitelist.merge(e.name, -1) { a: Int?, b: Int? ->
                Integer.sum(
                    a!!, b!!
                )
            }

        } else {

            try {
                var clearCLP = false
                //清空列表
                if (canLoginPlayer.size > 50) {
                    clearCLP = true
                }

                //初始化玩家
                canLoginPlayer.putIfAbsent(e.address.toString(), false)

                //增加连接计次
                if (Global.connectCount < 50) {
                    Global.connectCount++
                }

                //当连接计次到达一定水平后踢出
                if (Global.connectCount > 0) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Config.kickedForUnderAttack
                    )
                    return
                }

                //当玩家未通过重进验证是踢出
                if (!canLoginPlayer[e.address.toString()]!!) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Config.kickedForCheck
                    )
                    canLoginPlayer[e.address.toString()] = true
                }
                if (clearCLP) {
                    canLoginPlayer.clear()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {

            }
        }
    }
}