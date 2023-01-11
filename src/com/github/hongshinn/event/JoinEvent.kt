package com.github.hongshinn.event

import com.github.hongshinn.data.Global
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*


/**
 * @author hsn
 */
class JoinEvent : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {

        val player = e.player
        val ipAddress = e.player.address.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0].replace("/", "")

        val isToManyConnect = checkIpBearerLimit(e, ipAddress)

        //���ip�Ƿ������ҵ�¼
        if (Global.ipConnectCount[ipAddress]!! >= Global.playerCapPerIp!!
            && isToManyConnect
        ) {
            e.player.kickPlayer(Global.kickedForTooManyConnections)
            Bukkit.banIP(ipAddress)
        }

        //��������Ƿ�Υ��
        val isNameIllegal = checkIfTheNameIsIllegal(player)
        if (isNameIllegal) {
            e.joinMessage = null
            player.kickPlayer("��c������ֲ�����\"^[a-zA-Z0-9_]{3,16}\$\"�����������")
        }

        try {


            if (Global.connectCount < 30) {
                Global.canLogin[player.name] = true
            } else {
                Global.canLogin.putIfAbsent(player.name, false)
            }
            if (!isNameIllegal && !isToManyConnect && !Global.canLogin[player.name]!!) {
                Global.playerKickCount[player.name] = Global.computationalVerificationTimeout
                //��֤��
                val resultString = getVerificationString(player)
                sendQuestionMessage(player, resultString)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {

        }
    }

    private fun checkIfTheNameIsIllegal(player: Player): Boolean {
        var rtBool = false

        if (!java.util.regex.Pattern.matches("^[a-zA-Z0-9_]{3,16}$", player.name)) {
            rtBool = true
        }
        return rtBool
    }

    private fun checkIpBearerLimit(e: PlayerJoinEvent, ipAddress: String): Boolean {
        var rtBool = false

        try {

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        if (!Global.tempPlayerList.containsKey(e.player.name)) {
            //�ж����Ӽƴ��Ƿ����
            Global.ipConnectCount.putIfAbsent(ipAddress, 0)

            //������Ӽƴδ������������,��ban-ip
            if (Global.ipConnectCount[ipAddress]!! >= Global.maximumConnectionsPerIp!!) {
                rtBool = true
            } else {
                Global.tempPlayerList[e.player.name] = true
            }

            Global.ipConnectCount.merge(ipAddress, 1) { a: Int?, b: Int? ->
                Integer.sum(
                    a!!, b!!
                )
            }
        }

        return rtBool
    }

    private fun sendQuestionMessage(player: Player, resultString: StringBuilder) {
        if (Global.computationalVerificationEnabled || Global.dotSayVerificationEnabled) {
            player.sendMessage(Global.valueValidationChallengeText!!.replace("{question}", resultString.toString()))
            Global.captchaString[player.name] = resultString.toString()
        }
    }

    private fun getVerificationString(
        player: Player
    ): StringBuilder {
        var resultString = StringBuilder()
        if (Global.computationalVerificationEnabled) {
            val r = Random()
            val firstNumber = r.nextInt(5) + 1
            val secondNumber = r.nextInt(5) + 1
            val thirdNumber = r.nextInt(5) + 1

            var result: Int

            val firstOperator = r.nextInt(2)
            val secondOperator = r.nextInt(2)

            var isThePlusSign = false
            resultString = StringBuilder()
            val emptyCharacters = r.nextInt(2) + 1
            for (i in 0 until emptyCharacters) {
                resultString.append(" ")
            }
            if (firstOperator > 0) {
                result = firstNumber * secondNumber
                resultString.append(firstNumber).append("*").append(secondNumber)
            } else {
                isThePlusSign = true
                result = firstNumber + secondNumber
                resultString.append(firstNumber).append("+").append(secondNumber)
            }
            if (secondOperator > 0) {
                result *= thirdNumber
                if (isThePlusSign) {
                    resultString.insert(0, "(").append(")").append("*").append(thirdNumber)
                } else {
                    resultString.append("*").append(thirdNumber)
                }
            } else {
                result += thirdNumber
                resultString.append("+").append(thirdNumber)
            }
            resultString.append("=?")
            Global.captcha[player.name] = result
        } else {
            Global.canLogin[player.name] = true
        }
        return resultString
    }
}