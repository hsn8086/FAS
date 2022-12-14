package com.github.hsn8086.event

import com.github.hsn8086.data.Config
import com.github.hsn8086.data.Global
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.*

/**
 * @author hsn
 * @version 4.0
 */
class MoveEvent : Listener {
    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        try {

            //增加活跃度
            Global.activity.merge(e.player.name, 1) { a: Int, b: Int ->
                Integer.sum(
                    a, b
                )
            }
            //如果到达活跃度则白名单
            if (Global.activity[e.player.name]!! >= Config.whitelistActivityThreshold) {
                Global.activity[e.player.name] = 0
                Global.whitelist.merge(e.player.name, 1) { a: Int, b: Int ->
                    Integer.sum(
                        a, b
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {

        }
        val player = e.player

        //增加发言机会
        Global.spamCount.putIfAbsent(player.name, 0)
        if (Global.spamCount[player.name]!! > -(200 * Config.canStoreTheNumberOfSpeeches)) {
            Global.spamCount.merge(player.name, -(200 / 20 / Config.speakingInterval)) { a: Int, b: Int ->
                Integer.sum(
                    a, b
                )
            }
        }
        //检查登录状态
        Global.canLogin.putIfAbsent(player.name, false)
        if (!Global.canLogin[player.name]!!) {
            Global.loginTimeOutCount.putIfAbsent(player.name, 0)
            Global.loginTimeOutCount.merge(player.name, 1) { a: Int, b: Int ->
                Integer.sum(
                    a, b
                )
            }
            if (Global.loginTimeOutCount[player.name]!! > 400) {
                player.kickPlayer(Config.unauthenticatedActionWarning)
            }

            //若未验证则限制玩家移动过
            val r = Random()
            if (r.nextInt(5) + 1 > 2) {
                e.isCancelled = true
            }
            player.sendMessage(
                """
    ${Config.unauthenticatedActionWarning}
    ${Config.valueValidationChallengeText.replace("{question}", Global.captchaString[player.name]!!)}
    """.trimIndent()
            )
        }
    }
}